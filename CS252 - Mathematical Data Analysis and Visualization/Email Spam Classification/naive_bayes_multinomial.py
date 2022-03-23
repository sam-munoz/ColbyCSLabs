'''naive_bayes_multinomial.py
Naive Bayes classifier with Multinomial likelihood for discrete features
Samuel Munoz
CS 252 Data Analysis Visualization, Spring 2021
'''
import numpy as np


class NaiveBayes:
    '''Naive Bayes classifier using Multinomial likeilihoods (discrete data belonging to any
     number of classes)'''
    def __init__(self, num_classes):
        '''Naive Bayes constructor

        TODO:
        - Add instance variable for `num_classes`
        '''
        # class_priors: ndarray. shape=(num_classes,).
        #   Probability that a training example belongs to each of the classes
        #   For spam filter: prob training example is spam or ham
        self.class_priors = None
        # class_likelihoods: ndarray. shape=(num_classes, num_features).
        #   Probability that each word appears within class c
        self.class_likelihoods = None
        # num_classes: int.
        #   Number of classes in classifier
        self.num_classes = num_classes

    def train(self, data, y):
        '''Train the Naive Bayes classifier so that it records the "statistics" of the training set:
        class priors (i.e. how likely an email is in the training set to be spam or ham?) and the
        class likelihoods (the probability of a word appearing in each class — spam or ham)

        Parameters:
        -----------
        data: ndarray. shape=(num_samps, num_features). Data to learn / train on.
        y: ndarray. shape=(num_samps,). Corresponding class of each data sample.

        TODO:
        - Compute the instance variables self.class_priors and self.class_likelihoods needed for
        Bayes Rule. See equations in notebook.
        '''
        num_samps, num_features = data.shape
        
        # compute class priors
        self.class_priors = np.zeros(self.num_classes)
        
        # loop over each class
        for c in range(self.num_classes):
            # get all data samples of give class
            c_data = data[y == c]
            
            # compute priors
            self.class_priors[c] = c_data.size / data.size
            
        # compute likelihoods
        self.class_likelihoods = np.zeros( (self.num_classes, num_features) )
        
        # loop through all classes
        for c in range(self.num_classes):
            # compute total word count for the class
            c_data = data[y == c]
            c_total = np.sum(c_data)
            
            # loop through all words
            for word in range(num_features):
                # get all data from the given class
                c_data = data[y == c]

                # get given word column
                word_column = c_data[:, word]

                # compute sum of word count over total word count 
                self.class_likelihoods[c, word] = (np.sum(word_column) + 1) / (c_total + num_features)

    def predict(self, data):
        '''Combine the class likelihoods and priors to compute the posterior distribution. The
        predicted class for a test sample from `data` is the class that yields the highest posterior
        probability.

        Parameters:
        -----------
        data: ndarray. shape=(num_test_samps, num_features). Data to predict the class of
            Need not be the data used to train the network

        Returns:
        -----------
        ndarray of nonnegative ints. shape=(num_samps,). Predicted class of each test data sample.

        TODO:
        - For the test samples, we want to compute the log of the posterior by evaluating
        the the log of the right-hand side of Bayes Rule without the denominator (see notebook for
        equation). This can be done without loops using matrix multiplication or with a loop and
        a series of dot products.
        - Predict the class of each test sample according to the class that produces the largest
        log(posterior) probability (hint: can be done without loops).

        NOTE: Remember that you are computing the LOG of the posterior (see notebook for equation).
        NOTE: The argmax function could be useful here.
        '''
        num_test_samps, num_features = data.shape
        
        # use matrix multiplication method
        post = np.log(self.class_priors) + data @ np.log(self.class_likelihoods.T)
        
        # return argmax
        return np.argmax(post, axis=1)

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
        compare = y - y_pred
        
        # return proporation of correct answers
        return compare[compare == 0].size / y.size

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
    
    def k_fold_cross_validation(self, data, y, k):
        # get properties of data
        num_samps = data.shape[0]
        num_features = data.shape[1]

        # split data into k groups
        avg_size = int(num_samps / k)
        index = 0
        groups = []
        labels = []

        for i in range(k):
            if not i == k-1:
                groups.append(data[index:(index+avg_size)])
                labels.append(y[index:(index+avg_size)])
            else:
                groups.append(data[index:num_samps])
                labels.append(y[index:num_samps])
            index += avg_size

        # run model for all groups
        mse = np.array([])
        for group in range(1, k):
            # make train data set
            x_train = np.array(groups[0])
            y_train = np.array(labels[0])
            for i in range(k):
                if not group == i:
                    x_train = np.append(x_train, groups[i], axis=0)
                    y_train = np.append(y_train, labels[i])

            # make test data set
            x_test = groups[group]
            y_test = labels[group]

            # run Naive Bayes
            self.train(x_train, y_train)

            # get prediction
            pred = self.predict(x_test)

            # compute MSE (normally)
            # m = (y_test - pred)**2  / y_test.size
            m = self.accuracy(y_test, pred)
            mse = np.append(mse, m)

        # get average MSE
        return np.sum(mse) / mse.size