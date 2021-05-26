'''kmeans.py
Performs K-Means clustering
Samuel Munoz
CS 252 Mathematical Data Analysis Visualization, Spring 2021
'''
import numpy as np
import matplotlib.pyplot as plt
from palettable import cartocolors


class KMeans():
    def __init__(self, data=None):
        '''KMeans constructor

        (Should not require any changes)

        Parameters:
        -----------
        data: ndarray. shape=(num_samps, num_features)
        '''

        # k: int. Number of clusters
        self.k = None
        # centroids: ndarray. shape=(k, self.num_features)
        #   k cluster centers
        self.centroids = None
        # data_centroid_labels: ndarray of ints. shape=(self.num_samps,)
        #   Holds index of the assigned cluster of each data sample
        self.data_centroid_labels = None

        # inertia: float.
        #   Mean squared distance between each data sample and its assigned (nearest) centroid
        self.inertia = None

        # data: ndarray. shape=(num_samps, num_features)
        self.data = data
        # num_samps: int. Number of samples in the dataset
        self.num_samps = None
        # num_features: int. Number of features (variables) in the dataset
        self.num_features = None
        if data is not None:
            self.num_samps, self.num_features = data.shape

    def set_data(self, data):
        '''Replaces data instance variable with `data`.

        Reminder: Make sure to update the number of data samples and features!

        Parameters:
        -----------
        data: ndarray. shape=(num_samps, num_features)
        '''
        # update the field variable data
        self.data = data
        
        # update num_samps
        self.num_samps = self.data.shape[0]
        
        # update num_features
        self.num_features = self.data.shape[1]

    def get_data(self):
        '''Get a COPY of the data

        Returns:
        -----------
        ndarray. shape=(num_samps, num_features). COPY of the data
        '''
        # return a copy of data
        return self.data.copy()

    def get_centroids(self):
        '''Get the K-means centroids

        (Should not require any changes)

        Returns:
        -----------
        ndarray. shape=(k, self.num_features).
        '''
        return self.centroids

    def get_data_centroid_labels(self):
        '''Get the data-to-cluster assignments

        (Should not require any changes)

        Returns:
        -----------
        ndarray of ints. shape=(self.num_samps,)
        '''
        return self.data_centroid_labels

    def dist_pt_to_pt(self, pt_1, pt_2):
        '''Compute the Euclidean distance between data samples `pt_1` and `pt_2`

        Parameters:
        -----------
        pt_1: ndarray. shape=(num_features,)
        pt_2: ndarray. shape=(num_features,)

        Returns:
        -----------
        float. Euclidean distance between `pt_1` and `pt_2`.

        NOTE: Implement without any for loops (you will thank yourself later since you will wait
        only a small fraction of the time for your code to stop running)
        '''
        # subtract both lists (x_i - y_i)
        distance = pt_1 - pt_2
        
        # square the result (x_i - y_i)^2
        distance = distance**2
        
        # sum all components to get final result sum for all i of (x_i - y_i)^2 and take square root
        return np.sum(distance)**(1/2)

    def dist_pt_to_centroids(self, pt, centroids):
        '''Compute the Euclidean distance between data sample `pt` and and all the cluster centroids
        self.centroids

        Parameters:
        -----------
        pt: ndarray. shape=(num_features,)
        centroids: ndarray. shape=(C, num_features)
            C centroids, where C is an int.

        Returns:
        -----------
        ndarray. shape=(C,).
            distance between pt and each of the C centroids in `centroids`.

        NOTE: Implement without any for loops (you will thank yourself later since you will wait
        only a small fraction of the time for your code to stop running)
        '''
        # start by reshaping pt to form that the centroids are in (1, num_features)
        pt = np.reshape(pt, (1, pt.shape[0]))
        
        # then substract off pt from the centroids
        distance = centroids - pt
        
        # square result
        distance = distance**2
        
        # take sum across axis 1 and take the square root. return result
        return np.sum(distance, axis=1)**(1/2)

    def initialize(self, k):
        '''Initializes K-means by setting the initial centroids (means) to K unique randomly
        selected data samples

        Parameters:
        -----------
        k: int. Number of clusters

        Returns:
        -----------
        ndarray. shape=(k, self.num_features). Initial centroids for the k clusters.

        NOTE: Can be implemented without any for loops
        '''
        # construct random indices
        indices = np.random.choice(self.num_samps, k, replace=False)
        
        # return random intitial centroids
        self.centroids = self.data[indices]
        
        # set k field
        self.k = k
        
        return self.centroids

    def initialize_plusplus(self, k):
        '''Initializes K-means by setting the initial centroids (means) according to the K-means++
        algorithm

        (LA section only)

        Parameters:
        -----------
        k: int. Number of clusters

        Returns:
        -----------
        ndarray. shape=(k, self.num_features). Initial centroids for the k clusters.

        TODO:
        - Set initial centroid (i = 0) to a random data sample.
        - To pick the i-th centroid (i > 0)
            - Compute the distance between all data samples and i-1 centroids already initialized.
            - Create the distance-based probability distribution (see notebook for equation).
            - Select the i-th centroid by randomly choosing a data sample according to the probability
            distribution.
        '''
        # create centroids list (with first centroids)
        centroids = np.array([self.data[np.random.choice(self.num_samps)]])
        
        # run loop that collects all centroids up to k
        for i in range(k-1):
            # create list that stores minimum distance
            min_distances = np.array([])
        
            # loop over all samples
            for j in range(self.num_samps):
                # compute distances from all centroids to one sample
                one_samp_distances = self.dist_pt_to_centroids(self.data[j], centroids)

                # compare those distances 
                min_distances = np.append(min_distances, np.min(one_samp_distances))
                
            # compute my P(x)
            P = min_distances / np.sum(min_distances)
            
            # get random sample with weights P
            index = np.random.choice(self.num_samps, p=P)
            
            # add new sample to centroids
            new_centroid = self.data[index]
            new_centroid = np.reshape(new_centroid, (1, new_centroid.shape[0]))
            centroids = np.append(centroids, new_centroid, axis=0)
            
        # once all centroids have been computed, update centroids field
        self.centroids = centroids

    def cluster(self, k=2, tol=1e-2, max_iter=1000, init_method="random", verbose=False):
        '''Performs K-means clustering on the data

        Parameters:
        -----------
        k: int. Number of clusters
        tol: float. Terminate K-means if the difference between all the centroid values from the
        previous and current time step < `tol`.
        max_iter: int. Make sure that K-means does not run more than `max_iter` iterations.
        verbose: boolean. Print out debug information if set to True.

        Returns:
        -----------
        self.inertia. float. Mean squared distance between each data sample and its cluster mean
        int. Number of iterations that K-means was run for

        TODO:
        - Initialize K-means variables
        - Do K-means as long as the max number of iterations is not met AND the difference
        between every previous and current centroid value is > `tol`
        - Set instance variables based on computed values.
        (All instance variables defined in constructor should be populated with meaningful values)
        - Print out total number of iterations K-means ran for
        '''
        # set k
        self.k = k
        
        # initialize cluster
        if init_method == "random":
            self.initialize(self.k)
            if verbose:
                print("Cluster successfully initialized (method: random)")
        elif init_method == "kmeans++":
            self.initialize_plusplus(self.k)
            if verbose:
                print("Cluster successfully initialized (method: kmean++)")
        
        # keep track of total number of iterations (one has occured given initalization from above)
        iterate = 1
        
        # begin loop
        # loop only when we have not exceded total number of allowed loops
        while iterate < max_iter:
            # increase iterate
            iterate += 1
            
            if verbose:
                print(f"Loop {iterate}")
            
            # update data labels
            self.data_centroid_labels = self.update_labels(self.centroids)
            if verbose:
                print("Updated cluster labels")
            
            # update centroids
            self.centroids, distances = self.update_centroids(self.k, self.data_centroid_labels, self.centroids)
            if verbose:
                print("Updated centroids")
            
            # check if all distances are less than a given value; if yes, break of loop
            if distances.all() < tol:
                break
                
            # otherwise, loop again
        
        # if too many iteration ran, return NoneType
        if iterate == max_iter:
            return
        
        # otherwise, cluster was successful. compute inertia and return it with number of iterations
        return self.compute_inertia(), iterate
            
    def cluster_batch(self, k=2, n_iter=1, init_method="random", verbose=False):
        '''Run K-means multiple times, each time with different initial conditions.
        Keeps track of K-means instance that generates lowest inertia. Sets the following instance
        variables based on the best K-mean run:
        - self.centroids
        - self.data_centroid_labels
        - self.inertia

        Parameters:
        -----------
        k: int. Number of clusters
        n_iter: int. Number of times to run K-means with the designated `k` value.
        verbose: boolean. Print out debug information if set to True.
        '''
        # variable to store labels
        best_labels = None
        centroids = None
        inertia = 0
        list_iter = np.array([])
        
        # run loop
        for i in range(n_iter):
            # run cluster
            new_inertia, iterations = self.cluster(k=k, init_method=init_method, verbose=verbose)
            
            # add iteration to list_iter
            list_iter = np.append(list_iter, iterations)
            
            # if i==0, then populate inertia
            if i == 0:
                inertia = new_inertia
                centroids = self.centroids.copy()
                best_labels = self.data_centroid_labels.copy()
                
            # otherwise, check if this inertia is better than the last one. if so, store new best labels
            elif new_inertia < inertia:
                inertia = new_inertia
                centroids = self.centroids.copy()
                best_labels = self.data_centroid_labels.copy()
                
        # once the loop is over, set fields to best run data
        self.data_centroid_labels = best_labels
        self.centroids = centroids
        self.inertia = inertia
        
        # return average number of iterations
        return np.average(list_iter)

    def update_labels(self, centroids):
        '''Assigns each data sample to the nearest centroid

        Parameters:
        -----------
        centroids: ndarray. shape=(k, self.num_features). Current centroids for the k clusters.

        Returns:
        -----------
        ndarray of ints. shape=(self.num_samps,). Holds index of the assigned cluster of each data
            sample. These should be ints (pay attention to/cast your dtypes accordingly).

        Example: If we have 3 clusters and we compute distances to data sample i: [0.1, 0.5, 0.05]
        labels[i] is 2. The entire labels array may look something like this: [0, 2, 1, 1, 0, ...]
        '''
        # create variable for labels
        labels = np.zeros(self.num_samps, dtype=int)
        
        # loop through all samples
        for i in range(self.num_samps):
            # compute all distances 
            distances = self.dist_pt_to_centroids(self.data[i], centroids)
            
            # find the minimum value and get its index
            index = np.where(distances == np.min(distances))
            # print(index[0][0])
            
            # update labels
            labels[i] = index[0][0]
            
            # YOU STILL NEED TO DEAL TIE BREAKERS (i.e. there are two instances of min distance)
            
        # make sure that labels are ints
        labels = labels.astype(np.int64)
        
        # return completed labels
        return labels
    
    def update_centroids(self, k, data_centroid_labels, prev_centroids):
        '''Computes each of the K centroids (means) based on the data assigned to each cluster

        Parameters:
        -----------
        k: int. Number of clusters
        data_centroid_labels. ndarray of ints. shape=(self.num_samps,)
            Holds index of the assigned cluster of each data sample
        prev_centroids. ndarray. shape=(k, self.num_features)
            Holds centroids for each cluster computed on the PREVIOUS time step

        Returns:
        -----------
        new_centroids. ndarray. shape=(k, self.num_features).
            Centroids for each cluster computed on the CURRENT time step
        centroid_diff. ndarray. shape=(k, self.num_features).
            Difference between current and previous centroid values
        '''
        # make array for new centroids
        new_centroids = np.zeros( (k, self.num_features) )
        
        # print(data_centroid_labels.shape)
        # print(self.num_samps)
        
        # loop through each centriod and get 
        for i in range(k):
            # get all data points of a given cluster
            all_data = self.data[ np.where(data_centroid_labels == i) ]
            
            # compute mean of all data points
            new_centroids[i] = np.mean(all_data, axis=0, keepdims=True)
            
        # make array to compute distance between old and new centroids
        distance_old_new = new_centroids - prev_centroids
        
        # return results
        return new_centroids, distance_old_new
        

    def compute_inertia(self):
        '''Mean squared distance between every data sample and its assigned (nearest) centroid

        Parameters:
        -----------
        None

        Returns:
        -----------
        float. The average squared distance between every data sample and its assigned cluster centroid.
        '''
        # create list of centroids
        c_list = [self.centroids[self.data_centroid_labels[i]] for i in range(self.num_samps)]
        centroid_list = np.array(c_list)
        
        # subtract centroid
        inertia = self.data - centroid_list

        # sqaure results
        inertia = inertia**2
        
        # add each component for each sample
        inertia = np.sum(inertia, axis=1)
        
        # get total sum and store into inertia
        self.inertia = np.sum(inertia) / self.num_samps
        
        # return inertia
        return self.inertia
        
    def plot_clusters(self):
        '''Creates a scatter plot of the data color-coded by cluster assignment.

        TODO:
        - Plot samples belonging to a cluster with the same color.
        - Plot the centroids in black with a different plot marker.
        - The default scatter plot color palette produces colors that may be difficult to discern
        (especially for those who are colorblind). Make sure you change your colors to be clearly
        differentiable.
            You should use a palette Colorbrewer2 palette. Pick one with a generous
            number of colors so that you don't run out if k is large (e.g. 10).
        '''
        # get colors
        color = cartocolors.qualitative.Safe_10.mpl_colormap
    
        # plot data
        plt.scatter(self.data[:, 0], self.data[:, 1], c=self.data_centroid_labels, cmap=color)
        
        # plot centroids
        plt.scatter(self.centroids[:, 0], self.centroids[:, 1], marker="*", color="k")

    def elbow_plot(self, max_k):
        '''Makes an elbow plot: cluster number (k) on x axis, inertia on y axis.

        Parameters:
        -----------
        max_k: int. Run k-means with k=1,2,...,max_k.

        TODO:
        - Run k-means with k=1,2,...,max_k, record the inertia.
        - Make the plot with appropriate x label, and y label, x tick marks.
        '''
        # make empty ndarray list to store interia
        inertia = np.array([])
        
        # run max_k loop
        for i in range(1, max_k+1):
            inert, _ = self.cluster(i)
            inertia = np.append(inertia, [inert])
            
        # produce x-axis data
        frequency = np.arange(1, max_k+1)
            
        # produce plot
        plt.plot(frequency, inertia, "-")
        
        # give x- and y-labels
        plt.xlabel("k clusters")
        plt.ylabel("inertia")

    def replace_color_with_centroid(self):
        '''Replace each RGB pixel in self.data (flattened image) with the closest centroid value.
        Used with image compression after K-means is run on the image vector.

        Parameters:
        -----------
        None

        Returns:
        -----------
        None
        '''
        # loop through all samples
        for i in range(self.num_samps):
            # compute all distances from a sample to all centroids
            distances = self.dist_pt_to_centroids(self.data[i], self.centroids)
            
            # get the index of closest centroid to sample
            index = np.where(distances == np.min(distances))
            
            # replace RGB tuple with centroid tuple
            self.data[i] = self.centroids[index]

    def silhouette_score(self, pt_index):
        ''' Computes the silhouette score for a point at a given index 
        
        Parameters:
        -----------
        pt_index: index of the point selected
        
        Returns:
        -----------
        float: computes the silhouette score for the given point
        '''
        # compute average distance between point and all points in the cluster
        a = self.average_distance(pt_index)
        
        # compute minimum distance between point and all other points not in cluster of point
        b = self.min_average_distance(pt_index)
        
        # determine max value between a and b
        max_value = 0
        if a < b:
            max_value = b
        else:
            max_value = a
            
        # compute and return silhouette score
        # print(a)
        # print(b)
        # print(max_value)
        return (b - a) / max_value
        
    def average_distance(self, pt_index):
        ''' Computes the average distance between one point and all other points within in the same cluster. Helper method for sillhouette coefficient method
        
        Parameters:
        -----------
        pt_index: index of the point selected
        
        Returns:
        -----------
        float: average distance between the point and all other points in same cluster as the point
        '''
        # get label and value of point
        pt_label = self.data_centroid_labels[pt_index]
        pt = self.data[pt_index]
        
        # get all data points that belong to cluster of point
        subdata = self.data[ np.where(self.data_centroid_labels == pt_label) ]
        
        # compute all distances between pt and other points in cluster
        distances = self.dist_pt_to_centroids(pt, subdata)
        # print(distances)
        
        # return average all distances
        distances = distances / (distances.size - 1)
        return np.sum(distances) 
        
    def min_average_distance(self, pt_index):
        ''' Computes the average distance between one point and all other points within in the same cluster. Helper method for sillhouette coefficient method
        
        Parameters:
        -----------
        pt_index: index of the point selected
        
        Returns:
        -----------
        float: minimum average distance between the point and all other points in same cluster as the point
        '''
        # get label and value of point
        pt_label = self.data_centroid_labels[pt_index]
        pt = self.data[pt_index]
        # print(pt_label)
        
        # get all labels that's not pt_index
        other_labels = self.data_centroid_labels[self.data_centroid_labels != pt_label]
        # print(self.data_centroid_labels)
        # print(other_labels)
        
        # determine all labels that's not pt_index
        labels = np.array([])
        for i in range(len(other_labels)):
            if not other_labels[i] in labels:
                labels = np.append(labels, other_labels[i])
                
        # print(labels)
        # loop over all other clusters
        min_distance = 0
        for i in range(len(labels)):
            # get all data points from other cluster
            subdata = self.data[self.data_centroid_labels == labels[i]]
            
            # compute distances between pt and points in subdata
            distances = self.dist_pt_to_centroids(pt, subdata)
            
            # compute average distance
            avg_distance = np.average(distances)
            
            # only keep store minimum distance
            # base case: if i == 0, then no minimum distance is stored; store result
            if i == 0:
                min_distance = avg_distance
            # otherwise compare result
            elif avg_distance < min_distance:
                min_distance = avg_distance
                
        # once complete, return result
        return min_distance
        
    def average_silhouette_score(self):
        ''' Computes the average silhouette score across all data points
        
        Returns:
        -----------
        float: computes average silhouette score for all data points
        '''
        # loop across all data points in the set
        score = 0
        for index in range(self.num_samps):
            score += self.silhouette_score(index)
            
        # return average
        return score / self.num_samps
################# SOURCES #######################
# https://thispointer.com/numpy-amin-find-minimum-value-in-numpy-array-and-its-index/
# https://stackoverflow.com/questions/60551227/how-to-check-if-a-python-object-is-a-numpy-ndarray
# https://en.wikipedia.org/wiki/Silhouette_(clustering)
# https://medium.com/@cmukesh8688/silhouette-analysis-in-k-means-clustering-cefa9a7ad111
# https://towardsdatascience.com/silhouette-coefficient-validating-clustering-techniques-e976bb81d10c