'''email_preprocessor.py
Preprocess Enron email dataset into features for use in supervised learning algorithms
Samuel Munoz
CS 252 Data Analysis Visualization, Spring 2021
'''
import re
import os
import numpy as np


def tokenize_words(text):
    '''Transforms an email into a list of words.

    Parameters:
    -----------
    text: str. Sentence of text.

    Returns:
    -----------
    Python list of str. Words in the sentence `text`.

    This method is pre-filled for you (shouldn't require modification).
    '''
    # Define words as lowercase text with at least one alphabetic letter
    pattern = re.compile(r'[A-Za-z]+[\w^\']*|[\w^\']*[A-Za-z]+[\w^\']*')
    return pattern.findall(text.lower())


def count_words(email_path='data/enron'):
    '''Determine the count of each word in the entire dataset (across all emails)

    Parameters:
    -----------
    email_path: str. Relative path to the email dataset base folder.

    Returns:
    -----------
    word_freq: Python dictionary. Maps words (keys) to their counts (values) across the dataset.
    num_emails: int. Total number of emails in the dataset.

    TODO:
    - Descend into the dataset base directory -> class folders -> individual emails.
    - Read each email file as a string.
    - Use the `tokenize_words` function above to chunk it into a list of words.
    - Update the counts of each word in the dictionary.

    Hints:
    - Check out Python functions in the os and os.path modules for walking the directory structure.
    '''
    # create dictionary to store frequency
    word_count = {}
    
    # get ham and spam paths
    ham_path = email_path + "/ham/"
    spam_path = email_path + "/spam/"
    
    # get all filenames for ham_path email
    filenames = os.listdir(ham_path)
    for i in range(len(filenames)):
        filenames[i] = ham_path + filenames[i]
    
    # get all filenames for spam_path email
    spam_files = os.listdir(spam_path)
    for i in range(len(spam_files)):
        spam_files[i] = spam_path + spam_files[i]
    filenames = filenames + spam_files
    
    # loop through each filename and read file 
    for filename in filenames:
        # open file
        file = open(filename, "r")
        
        # read file
        content = file.read()
        
        # closes file
        file.close()
        
        # parse string
        words = tokenize_words(content)
        
        # update dictionary
        for word in words:
            if word in word_count:
                word_count[word] += 1
            else:
                word_count[word] = 1
                
    # returns dictionary and top words
    return word_count, len(filenames)
    
def find_top_words(word_freq, num_features=200):
    '''Given the dictionary of the words that appear in the dataset and their respective counts,
    compile a list of the top `num_features` words and their respective counts.

    Parameters:
    -----------
    word_freq: Python dictionary. Maps words (keys) to their counts (values) across the dataset.
    num_features: int. Number of top words to select.

    Returns:
    -----------
    top_words: Python list. Top `num_features` words in high-to-low count order.
    counts: Python list. Counts of the `num_features` words in high-to-low count order.
    '''
    # convert dictionary into list
    wc_list = list(word_freq.items())
    
    # convert to ndarray
    wc_list = np.array(wc_list) 
    
    # get frequency and index of list
    freq_list = wc_list[:, 1].astype(int)
    
    # loop through and find maximum value indices
    indices = []
    for i in range(num_features):
        if np.max(freq_list) == 0:
            break
        min_index = np.where(freq_list == np.max(freq_list))[0][0]
        indices.append(min_index)
        freq_list[min_index] = 0
    
    # get top words
    top_words = wc_list[indices]
    
    # return top words
    return top_words[:, 0].tolist(), (top_words[:, 1].astype(int)).tolist()

def make_feature_vectors(top_words, num_emails, email_path='data/enron'):
    '''Count the occurance of the top W (`num_features`) words in each individual email, turn into
    a feature vector of counts.

    Parameters:
    -----------
    top_words: Python list. Top `num_features` words in high-to-low count order.
    num_emails: int. Total number of emails in the dataset.
    email_path: str. Relative path to the email dataset base folder.

    Returns:
    -----------
    feats. ndarray. shape=(num_emails, num_features).
        Vector of word counts from the `top_words` list for each email.
    y. ndarray of nonnegative ints. shape=(num_emails,).
        Class index for each email (spam/ham)

    TODO:
    - Descend into the dataset base directory -> class folders -> individual emails.
    - Read each email file as a string.
    - Use `tokenize_words` to chunk it into a list of words.
    - Count the occurance of each word, ONLY THOSE THAT APPEAR IN `top_words`.

    HINTS:
    - Start with your code in `count_words` and modify as needed.
    '''
    # create vector feats and class labels
    feats = np.zeros( (num_emails, len(top_words)) )
    email_labels = np.zeros(num_emails)
    
    # create dictionary to store frequency
    word_count = {}
    
    # get ham and spam paths
    ham_path = email_path + "/ham/"
    spam_path = email_path + "/spam/"
    
    # get all filenames for ham_path email
    filenames = os.listdir(ham_path)
    for i in range(len(filenames)):
        filenames[i] = ham_path + filenames[i]
        email_labels[i] = 1
        
    
    # get all filenames for spam_path email
    spam_files = os.listdir(spam_path)
    for i in range(len(spam_files)):
        spam_files[i] = spam_path + spam_files[i]
        email_labels[len(filenames) + i] = 0
    filenames = filenames + spam_files
    
    # loop through each filename and read file 
    for i in range(len(filenames)):
        # set filename
        filename = filenames[i]
        
        # open file
        file = open(filename, "r")
        
        # read file
        content = file.read()
        
        # closes file
        file.close()
        
        # parse string
        words = tokenize_words(content)
            
        # update dictionary
        for word in words:
            if word in word_count:
                word_count[word] += 1
            else:
                word_count[word] = 1
                
        # update vector field
        for j in range(len(top_words)):
            word = top_words[j]
            if word in word_count:
                feats[i, j] = word_count[top_words[j]]
            else:
                feats[i, j] = 0
            
        # empty dictionary
        word_count.clear()
        
    # return feats and email_labels
    return feats, email_labels
            

def make_train_test_sets(features, y, test_prop=0.2, shuffle=True):
    '''Divide up the dataset `features` into subsets ("splits") for training and testing. The size
    of each split is determined by `test_prop`.

    Parameters:
    -----------
    features. ndarray. shape=(num_emails, num_features).
        Vector of word counts from the `top_words` list for each email.
    y. ndarray of nonnegative ints. shape=(num_emails,).
        Class index for each email (spam/ham)
    test_prop: float. Value between 0 and 1. What proportion of the dataset samples should we use
        for the test set? e.g. 0.2 means 20% of samples are used for the test set, the remaining
        80% are used in training.
    shuffle: boolean. Should we shuffle the data before splitting it into train/test sets?

    Returns:
    -----------
    x_train: ndarray. shape=(num_train_samps, num_features).
        Training dataset
    y_train: ndarray. shape=(num_train_samps,).
        Class values for the training set
    inds_train: ndarray. shape=(num_train_samps,).
        The index of each training set email in the original unshuffled dataset.
        For example: if we have originally N=5 emails in the dataset, their indices are
        [0, 1, 2, 3, 4]. Then we shuffle the data. The indices are now [4, 0, 3, 2, 1]
        let's say we put the 1st 3 samples in the training set and the remaining
        ones in the test set. inds_train = [4, 0, 3] and inds_test = [2, 1].
    x_test: ndarray. shape=(num_test_samps, num_features).
        Test dataset
    y_test:ndarray. shape=(num_test_samps,).
        Class values for the test set
    inds_test: ndarray. shape=(num_test_samps,).
        The index of each test set email in the original unshuffled dataset.
        For example: if we have originally N=5 emails in the dataset, their indices are
        [0, 1, 2, 3, 4]. Then we shuffle the data. The indices are now [4, 0, 3, 2, 1]
        let's say we put the 1st 3 samples in the training set and the remaining
        ones in the test set. inds_train = [4, 0, 3] and inds_test = [2, 1].
    '''
    inds = np.arange(y.size)
    if shuffle:
        features = features.copy()
        y = y.copy()

        inds = np.arange(y.size)
        np.random.shuffle(inds)
        features = features[inds]
        y = y[inds]

    # Your code here:
    # find the 1-test_prop to find index to split on
    i_split = int( features.shape[0] * (1-test_prop) )
    
    # get training data
    x_train = features[:i_split]
    y_train = y[:i_split]
    inds_train = inds[:i_split]
    
    # get test data
    x_test = features[i_split:]
    y_test = y[i_split:]
    inds_test = inds[i_split:]
    
    # return variables
    return x_train, y_train, inds_train, x_test, y_test, inds_test

def retrieve_emails(inds, email_path='data/enron'):
    '''Obtain the text of emails at the indices `inds` in the dataset.

    Parameters:
    -----------
    inds: ndarray of nonnegative ints. shape=(num_inds,).
        The number of ints is user-selected and indices are counted from 0 to num_emails-1
        (counting does NOT reset when switching to emails of another class).
    email_path: str. Relative path to the email dataset base folder.

    Returns:
    -----------
    Python list of str. len = num_inds = len(inds).
        Strings of entire raw emails at the indices in `inds`
    '''
    # get ham and spam paths
    ham_path = email_path + "/ham/"
    spam_path = email_path + "/spam/"
    
    # get all filenames for ham_path email and spam_path emails
    filenames = os.listdir(ham_path)
    filenames = filenames + os.listdir(spam_path)
        
    # convert filenames into ndarrarys
    nd_filenames = np.array(filenames)
    
    # verify that inds are valid indices
    if np.max(inds) >= nd_filenames.shape[0]:
        print("index too high")
        return
    
    if np.min(inds) < 0:
        print("index too low")
        return
    
    # index desired emails
    desired_emails = nd_filenames[inds]
    
    # read emails in ndarray above
    content = []
    for filename in desired_emails:
        # determine whehter file is spam or ham
        if os.path.isfile(ham_path + filename):
            name = ham_path + filename
        elif os.path.isfile(spam_path + filename):
            name = spam_path + filename
        else:
            print("error: file does not exist")
            return
        
        # open file
        file = open(name, "r")

        # read file
        c = file.read()

        # closes file
        file.close()
        
        # append email content into content list
        content.append(c) 
      
    # return content list
    return content