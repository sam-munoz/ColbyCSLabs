'''rbf_net.py
Radial Basis Function Neural Network
Samuel Munoz
CS 252: Mathematical Data Analysis Visualization, Spring 2021
'''
import numpy as np
import kmeans
import scipy.linalg

class RBF_Net:
    def __init__(self, num_hidden_units, num_classes):
        '''RBF network constructor

        Parameters:
        -----------
        num_hidden_units: int. Number of hidden units in network. NOTE: does NOT include bias unit
        num_classes: int. Number of output units in network. Equals number of possible classes in
            dataset

        TODO:
        - Define number of hidden units as an instance variable called `k` (as in k clusters)
            (You can think of each hidden unit as being positioned at a cluster center)
        - Define number of classes (number of output units in network) as an instance variable
        '''
        # prototypes: Hidden unit prototypes (i.e. center)
        #   shape=(num_hidden_units, num_features)
        self.prototypes = None
        # sigmas: Hidden unit sigmas: controls how active each hidden unit becomes to inputs that
        # are similar to the unit's prototype (i.e. center).
        #   shape=(num_hidden_units,)
        #   Larger sigma -> hidden unit becomes active to dissimilar inputs
        #   Smaller sigma -> hidden unit only becomes active to similar inputs
        self.sigmas = None
        # wts: Weights connecting hidden and output layer neurons.
        #   shape=(num_hidden_units+1, num_classes)
        #   The reason for the +1 is to account for the bias (a hidden unit whose activation is always
        #   set to 1).
        self.wts = None
        # k: number of clusters when computing KMeans
        # type: int
        self.k = num_hidden_units
        # num_classes: number of nodes in the output layer
        # type: int
        self.num_classes = num_classes

    def get_prototypes(self):
        '''Returns the hidden layer prototypes (centers)

        (Should not require any changes)

        Returns:
        -----------
        ndarray. shape=(k, num_features).
        '''
        return self.prototypes

    def get_num_hidden_units(self):
        '''Returns the number of hidden layer prototypes (centers/"hidden units").

        Returns:
        -----------
        int. Number of hidden units.
        '''
        return self.k

    def get_num_output_units(self):
        '''Returns the number of output layer units.

        Returns:
        -----------
        int. Number of output units
        '''
        return self.num_classes

    def avg_cluster_dist(self, data, centroids, cluster_assignments, kmeans_obj):
        '''Compute the average distance between each cluster center and data points that are
        assigned to it.

        Parameters:
        -----------
        data: ndarray. shape=(num_samps, num_features). Data to learn / train on.
        centroids: ndarray. shape=(k, num_features). Centroids returned from K-means.
        cluster_assignments: ndarray. shape=(num_samps,). Data sample-to-cluster-number assignment from K-means.
        kmeans_obj: KMeans. Object created when performing K-means.

        Returns:
        -----------
        ndarray. shape=(k,). Average distance within each of the `k` clusters.

        Hint: A certain method in `kmeans_obj` could be very helpful here!
        '''
        # loop through all centroids
        distances = np.zeros(centroids.shape[0])
        for index in range(centroids.shape[0]):
            # get data for a cluster
            cluster_data = data[cluster_assignments == index]
            
            # compute average distance
            d = kmeans_obj.dist_pt_to_centroids(centroids[index], cluster_data)
            distances[index] = np.sum(d) / d.size
            
        # return distances
        return distances

    def initialize(self, data):
        '''Initialize hidden unit centers using K-means clustering and initialize sigmas using the
        average distance within each cluster

        Parameters:
        -----------
        data: ndarray. shape=(num_samps, num_features). Data to learn / train on.

        TODO:
        - Determine `self.prototypes` (see constructor for shape). Prototypes are the centroids
        returned by K-means. It is recommended to use the 'batch' version of K-means to reduce the
        chance of getting poor initial centroids.
            - To increase the chance that you pick good centroids, set the parameter controlling the
            number of iterations > 1 (e.g. 5)
        - Determine self.sigmas as the average distance between each cluster center and data points
        that are assigned to it. Hint: You implemented a method to do this!
        '''
        # create KMeans object
        kmeans_obj = kmeans.KMeans(data)
        
        # create variables that control parameters in KMeans algorithm
        n_iter = 5
        
        # cluster data
        kmeans_obj.cluster_batch(self.k, n_iter, init_method="kmeans++")
        
        # get prototypes and labels
        self.prototypes = kmeans_obj.centroids
        data_labels = kmeans_obj.data_centroid_labels
        
        # compute sigmas 
        self.sigmas = self.avg_cluster_dist(data, self.prototypes, data_labels, kmeans_obj)
        
    # helper method: computes dot product of two arrays a and b
    def dot_product(self, a, b):
        # check if length of two columns are the same
        if not a.shape[0] == b.shape[0]:
            print("ERROR: cannot take dot product of vectors with different length")
            return None
        
        # otherwise, compute dot product and return that value
        return np.sum(a * b)
            
    # helper method: computes the length of a column vector a
    def length(self, a):
        return (np.sum([a[i]**2 for i in range(0, a.shape[0])]))**0.5
            
    def qr_decomposition(self, A):
        '''Performs a QR decomposition on the matrix A.
        '''
        # set Q
        Q = A.copy()
        
        # begin QR factorization. start by looping through matrix and compute Q
        for i in range(0, Q.shape[1]):
            for j in range(0, i):
                # first substract in column i in self.A the dot product of column i and j and multiply that to column j
                Q[:, i] -= self.dot_product(Q[:, i], Q[:, j]) * Q[:, j]
                
            # once all subtraction as occured, then normalize by length
            Q[:, i] = Q[:, i] / self.length(Q[:, i])
            
            
        # Q computed; determine R
        R = Q.T @ A
        
        # return Q and R
        return Q, R
    
    def linear_regression(self, A, y):
        '''Performs linear regression
        CS251: Adapt your SciPy lstsq code from the linear regression project.
        CS252: Adapt your QR-based linear regression solver

        Parameters:
        -----------
        A: ndarray. shape=(num_data_samps, num_features).
            Data matrix for independent variables.
        y: ndarray. shape=(num_data_samps, 1).
            Data column for dependent variable.

        Returns
        -----------
        c: ndarray. shape=(num_features+1,)
            Linear regression slope coefficients for each independent var AND the intercept term

        NOTE: Remember to handle the intercept ("homogenous coordinate")
        '''
        # add intercept to A
        A = np.hstack( (A, np.ones((A.shape[0], 1))) )
        
        # decompose A
        Q, R = self.qr_decomposition(A)
        
        # use scipy.linalg.solve_triangular to find c
        c = scipy.linalg.solve_triangular(R, Q.T @ y)
        
        # return c array
        return c

    def distance_sq(self, pt, data):
        '''Helper method: Returns the distance squared between prototype and all data samples

        Parameters:
        -----------
        pt: ndarray. shape=(1, num_features). Find distance between this prototypes and all data points.
        data: ndarray. shape=(num_samples, num_features)

        Returns:
        -----------
        ndarray. shape=(num_samps, 1).
        Returns the distance squared between pt and all prototypes
        '''
        # compute distance 
        distance = data - pt
        distance = distance**2
        distance = np.sum(distance, axis=1)
        return distance
    
    def hidden_act(self, data):
        '''Compute the activation of the hidden layer units

        Parameters:
        -----------
        data: ndarray. shape=(num_samps, num_features). Data to learn / train on.

        Returns:
        -----------
        ndarray. shape=(num_samps, k).
            Activation of each unit in the hidden layer to each of the data samples.
            Do NOT include the bias unit activation.
            See notebook for refresher on the activation equation
        '''
        # create hidden activation matrix
        h = np.zeros( (data.shape[0], self.k) )
        
        # create variables for computation
        epsilon = 1 * 10**(-8)
        
        # for each hidden layer
        for j in range(self.k):
            # compute distance squared between prototype j and all data points
            d = self.distance_sq(self.prototypes[j], data)
            
            # compute hidden activation for all samples
            d = - d / (2 * self.sigmas[j]**2 + epsilon)
            d = np.e**d
            
            # set result to hidden activation matrix
            h[:, j] = d
            
        # return hidden activation matrix
        return h

    def output_act(self, hidden_acts):
        '''Compute the activation of the output layer units

        Parameters:
        -----------
        hidden_acts: ndarray. shape=(num_samps, k).
            Activation of the hidden units to each of the data samples.
            Does NOT include the bias unit activation.

        Returns:
        -----------
        ndarray. shape=(num_samps, num_output_units).
            Activation of each unit in the output layer to each of the data samples.

        NOTE:
        - Assumes that learning has already taken place
        - Can be done without any for loops.
        - Don't forget about the bias unit!
        '''
        # add column of ones to hidden_acts
        hidden_acts = np.hstack( (hidden_acts, np.ones( (hidden_acts.shape[0], 1) )) )
        
        # compute activation scores for output layer units
        return hidden_acts @ self.wts

    def train(self, data, y):
        '''Train the radial basis function network

        Parameters:
        -----------
        data: ndarray. shape=(num_samps, num_features). Data to learn / train on.
        y: ndarray. shape=(num_samps,). Corresponding class of each data sample.

        Goal: Set the weights between the hidden and output layer weights (self.wts) using
        linear regression. The regression is between the hidden layer activation (to the data) and
        the correct classes of each training sample. To solve for the weights going FROM all of the
        hidden units TO output unit c, recode the class vector `y` to 1s and 0s:
            1 if the class of a data sample in `y` is c
            0 if the class of a data sample in `y` is not c

        Notes:
        - Remember to initialize the network (set hidden unit prototypes and sigmas based on data).
        - Pay attention to the shape of self.wts in the constructor above. Yours needs to match.
        - The linear regression method handles the bias unit.
        '''
        # initialize network
        self.initialize(data)
        
        # construct hidden unit activations
        h = self.hidden_act(data)
        
        # use linear regression to compute weights
        self.wts = np.zeros( (h.shape[1] + 1, self.num_classes) )
        for c in range(self.num_classes):
            # determine y for given class for regression
            y_regression = y == c
            
            # compute linear regression line for given 
            wt_vector = self.linear_regression(h, y_regression)
            
            # store result in weight matrix
            self.wts[:, c] = wt_vector
            
    def predict(self, data):
        '''Classify each sample in `data`

        Parameters:
        -----------
        data: ndarray. shape=(num_samps, num_features). Data to predict classes for.
            Need not be the data used to train the network

        Returns:
        -----------
        ndarray of nonnegative ints. shape=(num_samps,). Predicted class of each data sample.

        TODO:
        - Pass the data thru the network (input layer -> hidden layer -> output layer).
        - For each data sample, the assigned class is the index of the output unit that produced the
        largest activation.
        '''
        # get hidden activation for data
        h = self.hidden_act(data)
        
        # compute output activations
        activations = self.output_act(h)
        
        # for each sample
        pred = np.zeros(data.shape[0])
        for i in range(activations.shape[0]):
            # find argmax of column
            max_i = np.argmax(activations[i])
            
            # save prediction
            pred[i] = max_i
            
        # return predictions
        return pred
    
    def accuracy(self, y, y_pred):
        '''Computes accuracy based on percent correct: Proportion of predicted class labels `y_pred`
        that match the true values `y`.

        Parameters:
        -----------
        y: ndarray. shape=(num_data_sams,)
            Ground-truth, known class labels for each data sample
        y_pred: ndarray. shape=(num_data_sams,)
            Predicted class labels by the model for each data sample

        Returns:
        -----------
        float. Between 0 and 1. Proportion correct classification.

        NOTE: Can be done without any loops
        '''
        # compare results
        compare = y == y_pred
        compare = compare.astype(int)
        
        # return accuracy
        return compare[compare == 1].size / compare.size


class RBF_Reg_Net(RBF_Net):
    '''RBF Neural Network configured to perform regression
    '''
    def __init__(self, num_hidden_units, num_classes, h_sigma_gain=5):
        '''RBF regression network constructor

        Parameters:
        -----------
        num_hidden_units: int. Number of hidden units in network. NOTE: does NOT include bias unit
        num_classes: int. Number of output units in network. Equals number of possible classes in
            dataset
        h_sigma_gain: float. Multiplicative gain factor applied to the hidden unit variances

        TODO:
        - Create an instance variable for the hidden unit variance gain
        '''
        super().__init__(num_hidden_units, num_classes)
        
        '''
        g: int. hidden unit variance gain
        '''
        self.g = h_sigma_gain

    def hidden_act(self, data):
        '''Compute the activation of the hidden layer units

        Parameters:
        -----------
        data: ndarray. shape=(num_samps, num_features). Data to learn / train on.

        Returns:
        -----------
        ndarray. shape=(num_samps, k).
            Activation of each unit in the hidden layer to each of the data samples.
            Do NOT include the bias unit activation.
            See notebook for refresher on the activation equation

        TODO:
        - Copy-and-paste your classification network code here.
        - Modify your code to apply the hidden unit variance gain to each hidden unit variance.
        '''
        # create hidden activation matrix
        h = np.zeros( (data.shape[0], self.k) )
        
        # create variables for computation
        epsilon = 1 * 10**(-8)
        
        # for each hidden layer
        for j in range(self.k):
            # compute distance squared between prototype j and all data points
            d = self.distance_sq(self.prototypes[j], data)
            
            # compute hidden activation for all samples
            d = - d / (2 * self.g * self.sigmas[j]**2 + epsilon)
            d = np.e**d
            
            # set result to hidden activation matrix
            h[:, j] = d
            
        # return hidden activation matrix
        return h

    def train(self, data, y):
        '''Train the radial basis function network

        Parameters:
        -----------
        data: ndarray. shape=(num_samps, num_features). Data to learn / train on.
        y: ndarray. shape=(num_samps,). Corresponding class of each data sample.

        Goal: Set the weights between the hidden and output layer weights (self.wts) using
        linear regression. The regression is between the hidden layer activation (to the data) and
        the desired y output of each training sample.

        Notes:
        - Remember to initialize the network (set hidden unit prototypes and sigmas based on data).
        - Pay attention to the shape of self.wts in the constructor above. Yours needs to match.
        - The linear regression method handles the bias unit.

        TODO:
        - Copy-and-paste your classification network code here, modifying it to perform regression on
        the actual y values instead of the y values that match a particular class. Your code should be
        simpler than before.
        - You may need to squeeze the output of your linear regression method if you get shape errors.
        '''
        # initialize network
        self.initialize(data)
        
        # construct hidden unit activations
        h = self.hidden_act(data)
        
        # use linear regression to compute weights
        wt_vector = self.linear_regression(h, y)
        
        # store results
        self.wts = wt_vector
            
    def predict(self, data):
        '''Classify each sample in `data`

        Parameters:
        -----------
        data: ndarray. shape=(num_samps, num_features). Data to predict classes for.
            Need not be the data used to train the network

        Returns:
        -----------
        ndarray. shape=(num_samps, num_output_neurons). Output layer neuronPredicted "y" value of
            each sample in `data`.

        TODO:
        - Copy-and-paste your classification network code here, modifying it to return the RAW
        output neuron activaion values. Your code should be simpler than before.
        '''
        # get hidden activation for data
        h = self.hidden_act(data)
        
        # compute output activations
        activations = self.output_act(h)
        
        # return predictions
        return activations
