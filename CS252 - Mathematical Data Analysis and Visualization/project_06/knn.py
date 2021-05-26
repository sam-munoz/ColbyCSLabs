'''knn.py
K-Nearest Neighbors algorithm for classification
Samuel Munoz
CS 251 Data Analysis Visualization, Spring 2021
'''
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.colors import ListedColormap
from palettable import cartocolors


class KNN:
    '''K-Nearest Neighbors supervised learning algorithm'''
    def __init__(self, num_classes):
        '''KNN constructor

        TODO:
        - Add instance variable for `num_classes`
        '''
        # exemplars: ndarray. shape=(num_train_samps, num_features).
        #   Memorized training examples
        self.exemplars = None
        # classes: ndarray. shape=(num_train_samps,).
        #   Classes of memorized training examples
        self.classes = None
        # num_classes: int.
        #   Stores number of classes in KNN object
        self.num_classes = num_classes

    def train(self, data, y):
        '''Train the KNN classifier on the data `data`, where training samples have corresponding
        class labels in `y`.

        Parameters:
        -----------
        data: ndarray. shape=(num_train_samps, num_features). Data to learn / train on.
        y: ndarray. shape=(num_train_samps,). Corresponding class of each data sample.

        TODO:
        - Set the `exemplars` and `classes` instance variables such that the classifier memorizes
        the training data.
        '''
        # memorize data
        self.exemplars = data
        
        # memorize its classes
        self.classes = y

    def predict(self, data, k):
        '''Use the trained KNN classifier to predict the class label of each test sample in `data`.
        Determine class by voting: find the closest `k` training exemplars (training samples) and
        the class is the majority vote of the classes of these training exemplars.

        Parameters:
        -----------
        data: ndarray. shape=(num_test_samps, num_features). Data to predict the class of
            Need not be the data used to train the network.
        k: int. Determines the neighborhood size of training points around each test sample used to
            make class predictions. In other words, how many training samples vote to determine the
            predicted class of a nearby test sample.

        Returns:
        -----------
        ndarray of nonnegative ints. shape=(num_test_samps,). Predicted class of each test data
        sample.

        TODO:
        - Compute the distance from each test sample to all the training exemplars.
        - Among the closest `k` training exemplars to each test sample, count up how many belong
        to which class.
        - The predicted class of the test sample is the majority vote.
        '''
        # ndarray to store class of correspond test sample
        test_samples_class = np.zeros(data.shape[0])
        
        # ndarray to store distances of all point to list
        # for i-th entry: distances[i] = (i-th distance, i-th index)
        distances = np.zeros( (k, 2) )
        
        
        # loop over all test samples
        for index in range(data.shape[0]):
            sample = data[index]
            
            # compute distances
            distance = self.exemplars - sample
            distance = distance**2
            distance = np.sum(distance, axis=1)
            distance = distance**(1/2)
            
            # get k closest distances
            indices = np.zeros(k)
            for i in range(k):
                min_index = np.where(distance == np.min(distance))[0]
                indices[i] = min_index[0]
                distance[min_index[0]] = np.max(distance)
            
            # get classes for these exemplars and vote
            c_classes = self.classes[indices.astype(int)].astype(int)
            tally = np.zeros(self.num_classes)
            for c in c_classes:
                tally[c] += 1
                
            # get maximum
            n_max = np.where(tally == np.max(tally))[0]
            
            # select class randomly
            test_samples_class[index] = n_max[0]
            
        return test_samples_class
    
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
        # compare lists
        compare = y == y_pred
        valid = compare[compare == True]
        return valid.size / compare.size

    def plot_predictions(self, k, n_sample_pts):
        '''Paints the data space in colors corresponding to which class the classifier would
         hypothetically assign to data samples appearing in each region.

        Parameters:
        -----------
        k: int. Determines the neighborhood size of training points around each test sample used to
            make class predictions. In other words, how many training samples vote to determine the
            predicted class of a nearby test sample.
        n_sample_pts: int.
            How many points to divide up the input data space into along the x and y axes to plug
            into KNN at which we are determining the predicted class. Think of this as regularly
            spaced 2D "fake data" that we generate and plug into KNN and get predictions at.

        TODO:
        - Pick a discrete/qualitative color scheme. We suggest, like in the clustering project, to
        use a ColorBrewer color palette. List of possible ones here:
        https://github.com/CartoDB/CartoColor/wiki/CARTOColor-Scheme-Names
            - An example: cartocolors.qualitative.Safe_4.mpl_colors
            - The 4 stands for the number of colors in the palette. For simplicity, you can assume
            that we're hard coding this at 4 for 4 classes.
        - Each ColorBrewer palette is a Python list. Wrap this in a `ListedColormap` object so that
        matplotlib can parse it (already imported above).
        - Make an ndarray of length `n_sample_pts` of regularly spaced points between -40 and +40.
        - Call `np.meshgrid` on your sampling vector to get the x and y coordinates of your 2D
        "fake data" sample points in the square region from [-40, 40] to [-40, 40].
            - Example: x, y = np.meshgrid(samp_vec, samp_vec)
        - Combine your `x` and `y` sample coordinates into a single ndarray and reshape it so that
        you can plug it in as your `data` in self.predict.
            - Shape of `x` should be (n_sample_pts, n_sample_pts). You want to make your input to
            self.predict of shape=(n_sample_pts*n_sample_pts, 2).
        - Reshape the predicted classes (`y_pred`) in a square grid format for plotting in 2D.
        shape=(n_sample_pts, n_sample_pts).
        - Use the `plt.pcolormesh` function to create your plot. Use the `cmap` optional parameter
        to specify your discrete ColorBrewer color palette.
        - Add a colorbar to your plot
        '''
        # get color palette
        color = cartocolors.qualitative.Safe_4.mpl_colors
        
        # make colormap
        c_map = ListedColormap(color)
        
        # create points
        x_pts = np.linspace(-40, 40, n_sample_pts)
        
        # create mesh
        x, y = np.meshgrid(x_pts, x_pts)
        
        # flatten x and y
        flat_x = np.reshape(x, x.shape[0] * x.shape[1])
        flat_y = np.reshape(y, y.shape[0] * y.shape[1])
        
        # create points
        mesh = np.column_stack( (flat_x, flat_y) )
        
        # predict points and reshape
        pred = self.predict(mesh, k)
        pred = np.reshape(pred, x.shape)
        
        # plot results
        plot = plt.pcolormesh(x, y, pred, cmap=c_map)
        
        # add colorbar
        plt.colorbar(plot)
        
    def confusion_matrix(self, y, y_pred):
        '''Create a confusion matrix based on the ground truth class labels (`y`) and those predicted
        by the classifier (`y_pred`).

        Recall: the rows represent the "actual" ground truth labels, the columns represent the
        predicted labels.

        Parameters:
        -----------
        y: ndarray. shape=(num_data_samps,)
            Ground-truth, known class labels for each data sample
        y_pred: ndarray. shape=(num_data_samps,)
            Predicted class labels by the model for each data sample

        Returns:
        -----------
        ndarray. shape=(num_classes, num_classes).
            Confusion matrix
        '''
        # To get the number of classes, you can use the np.unique
        # function to identify the number of unique categories in the
        # y matrix.

        # get number of unique classes in data set
        num_classes = np.unique(y).shape[0]

        # create confusion matrix ndarray
        conf_matrix = np.zeros( (num_classes, num_classes) )

        # loop through each sample
        for sample in range(y.shape[0]):
            actual_index = int(y[sample])
            pred_index = int(y_pred[sample])
            conf_matrix[actual_index, pred_index] += 1

        return conf_matrix
    
    def k_fold_cross_validation(self, data, y, k_0, k_1):
        # k_0: k for KNN
        # k_1: k for this algorithm
        # get properties of data
        num_samps = data.shape[0]
        num_features = data.shape[1]

        # split data into k groups
        avg_size = int(num_samps / k_1)
        index = 0
        groups = []
        labels = []

        for i in range(k_1):
            if not i == k_1-1:
                groups.append(data[index:(index+avg_size)])
                labels.append(y[index:(index+avg_size)])
            else:
                groups.append(data[index:num_samps])
                labels.append(y[index:num_samps])
            index += avg_size

        # run model for all groups
        mse = np.array([])
        for group in range(1, k_1):
            # make train data set
            x_train = np.array(groups[0])
            y_train = np.array(labels[0])
            for i in range(k_1):
                if not group == i:
                    x_train = np.append(x_train, groups[i], axis=0)
                    y_train = np.append(y_train, labels[i])

            # make test data set
            x_test = groups[group]
            y_test = labels[group]

            # run Naive Bayes
            self.train(x_train, y_train)

            # get prediction
            pred = self.predict(x_test, k_0)

            # compute MSE (normally)
            # m = (y_test - pred)**2  / y_test.size
            m = self.accuracy(y_test, pred)
            mse = np.append(mse, m)

        # get average MSE
        return np.sum(mse) / mse.size