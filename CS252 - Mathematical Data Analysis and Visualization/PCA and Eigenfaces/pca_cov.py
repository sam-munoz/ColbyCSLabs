'''pca_cov.py
Performs principal component analysis using the covariance matrix approach
YOUR NAME HERE
CS 251 Data Analysis Visualization
Spring 2021
'''
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd


class PCA_COV:
    '''
    Perform and store principal component analysis results
    '''

    def __init__(self, data):
        '''

        Parameters:
        -----------
        data: pandas DataFrame. shape=(num_samps, num_vars)
            Contains all the data samples and variables in a dataset.

        (No changes should be needed)
        '''
        self.data = data

        # vars: Python list. len(vars) = num_selected_vars
        #   String variable names selected from the DataFrame to run PCA on.
        #   num_selected_vars <= num_vars
        self.vars = None

        # A: ndarray. shape=(num_samps, num_selected_vars)
        #   Matrix of data selected for PCA
        self.A = None

        # normalized: boolean.
        #   Whether data matrix (A) is normalized by self.pca
        self.normalized = None

        # A_proj: ndarray. shape=(num_samps, num_pcs_to_keep)
        #   Matrix of PCA projected data
        self.A_proj = None

        # e_vals: ndarray. shape=(num_pcs,)
        #   Full set of eigenvalues (ordered large-to-small)
        self.e_vals = None
        # e_vecs: ndarray. shape=(num_selected_vars, num_pcs)
        #   Full set of eigenvectors, corresponding to eigenvalues ordered large-to-small
        self.e_vecs = None

        # prop_var: Python list. len(prop_var) = num_pcs
        #   Proportion variance accounted for by the PCs (ordered large-to-small)
        self.prop_var = None

        # cum_var: Python list. len(cum_var) = num_pcs
        #   Cumulative proportion variance accounted for by the PCs (ordered large-to-small)
        self.cum_var = None

    def get_prop_var(self):
        '''(No changes should be needed)'''
        return self.prop_var

    def get_cum_var(self):
        '''(No changes should be needed)'''
        return self.cum_var

    def get_eigenvalues(self):
        '''(No changes should be needed)'''
        return self.e_vals

    def get_eigenvectors(self):
        '''(No changes should be needed)'''
        return self.e_vecs

    def covariance_matrix(self, data):
        '''Computes the covariance matrix of `data`

        Parameters:
        -----------
        data: ndarray. shape=(num_samps, num_vars)
            `data` is NOT centered coming in, you should do that here.

        Returns:
        -----------
        ndarray. shape=(num_vars, num_vars)
            The covariance matrix of centered `data`

        NOTE: You should do this wihout any loops
        NOTE: np.cov is off-limits here — compute it from "scratch"!
        '''
        # compute mean for all variables
        means = np.mean(data, axis=0)
        
        # center data around mean for each column
        center_data = data - means
        
        # perform correct matrix multiplication
        covar = 1/(center_data.shape[0]-1) * (center_data.T @ center_data)
        
        # return covar matrix
        return covar

    def compute_prop_var(self, e_vals):
        '''Computes the proportion variance accounted for by the principal components (PCs).

        Parameters:
        -----------
        e_vals: ndarray. shape=(num_pcs,)

        Returns:
        -----------
        Python list. len = num_pcs
            Proportion variance accounted for by the PCs
        '''
        # compute sum of all variance
        total_var = np.sum(e_vals)
        
        # return the e_vals list divided by total_var
        return (e_vals / total_var).tolist()

    def compute_cum_var(self, prop_var):
        '''Computes the cumulative variance accounted for by the principal components (PCs).

        Parameters:
        -----------
        prop_var: Python list. len(prop_var) = num_pcs
            Proportion variance accounted for by the PCs, ordered largest-to-smallest
            [Output of self.compute_prop_var()]

        Returns:
        -----------
        Python list. len = num_pcs
            Cumulative variance accounted for by the PCs
        '''
        # assume that there is no way to do this without loops
        
        # variable that contains total sum of elements it has visited
        total_sum = prop_var[0]
        
        # loop to run through all values
        for i in range(1, len(prop_var)):
            prop_var[i] = prop_var[i] + total_sum
            total_sum = prop_var[i]
        
        # return list
        return prop_var
        
        # return np.cumsum(prop_var, dtype="float")
        
    # helper function: bubble sort to sort out the eigen values and vectors
    def bubble_sort(self, alist):
        for i in range(0, len(alist)):
            for j in range(i+1, len(alist)):
                if alist[i][0] < alist[j][0]:
                    tmp = alist[i].copy()
                    alist[i] = alist[j]
                    alist[j] = tmp
        return alist

    def pca(self, vars, normalize=False):
        '''Performs PCA on the data variables `vars`

        Parameters:
        -----------
        vars: Python list of strings. len(vars) = num_selected_vars
            1+ variable names selected to perform PCA on.
            Variable names must match those used in the `self.data` DataFrame.
        normalize: boolean.
            If True, normalize each data variable so that the values range from 0 to 1.

        NOTE: Leverage other methods in this class as much as possible to do computations.

        TODO:
        - Select the relevant data (corresponding to `vars`) from the data pandas DataFrame
        then convert to numpy ndarray for forthcoming calculations.
        - If `normalize` is True, normalize the selected data so that each variable (column)
        ranges from 0 to 1 (i.e. normalize based on the dynamic range of each variable).
            - Before normalizing, create instance variables containing information that would be
            needed to "undo" or reverse the normalization on the selected data.
        - Make sure to compute everything needed to set all instance variables defined in constructor,
        except for self.A_proj (this will happen later).
        '''
        # set field values
        self.vars = vars
        
        # get all columns of data
        df = self.data.filter(items=vars)
        
        # convert datafram to ndarray
        d = df.to_numpy()
        
        # get mean of data
        self.means = np.mean(d, axis=0)
        
        # if normaalize is True, then normalize data
        if normalize:
            # get means and ranges for each variable
            self.means = np.mean(d, axis=0)
            self.ranges = np.max(d, axis=0) - np.min(d, axis=0)
            
            # shift and scale data d by lists above
            d = (d - self.means) / self.ranges
            
            # update normalized field
            self.normalized = True
            
        # store self.A the PCs
        self.A = d
        
        # get covar matrix
        covar = self.covariance_matrix(d)
        
        # compute all eigen vectors and eigen values
        e_vals, self.e_vecs = np.linalg.eig(covar)
        
        # sort all e_vals and e_vecs
        sort_vals = np.append([e_vals], [np.arange(0, len(e_vals))], axis=0)
        sort_vals = sort_vals.T
        e_vals = self.bubble_sort(sort_vals)
        for i in range(len(sort_vals)):
            i_new = int(sort_vals[i, 1])
            tmp = self.e_vecs[:, i_new].copy()
            self.e_vecs[:, i_new] = self.e_vecs[:, i]
            self.e_vecs[:, i] = tmp
        self.e_vals = e_vals[:, 0]
            
        # get prop_var
        self.prop_var = self.compute_prop_var(self.e_vals)
        
        # get cum_var
        self.cum_var = self.compute_cum_var(self.prop_var)
        
        
    def elbow_plot(self, num_pcs_to_keep=None):
        '''Plots a curve of the cumulative variance accounted for by the top `num_pcs_to_keep` PCs.
        x axis corresponds to top PCs included (large-to-small order)
        y axis corresponds to proportion variance accounted for

        Parameters:
        -----------
        num_pcs_to_keep: int. Show the variance accounted for by this many top PCs.
            If num_pcs_to_keep is None, show variance accounted for by ALL the PCs (the default).

        NOTE: Make plot markers at each point. Enlarge them so that they look obvious.
        NOTE: Reminder to create useful x and y axis labels.
        NOTE: Don't write plt.show() in this method
        '''
        # store number of PCs to keep
        if not num_pcs_to_keep == None:
            keep_pc = num_pcs_to_keep
        else:
            keep_pc = len(self.cum_var)
        
        # create x-values
        x = np.arange(1, keep_pc+1)
        
        # plot data
        plt.plot(x, self.cum_var[0:keep_pc+1], "k.")
        
        # give title
        plt.title("Cummulative Variance Proportion for Every PC")
        
        # xlabel
        plt.xlabel("Num of PCs")
        
        # ylabel
        plt.ylabel("Cummu. Variance")

    def pca_project(self, pcs_to_keep):
        '''Project the data onto `pcs_to_keep` PCs (not necessarily contiguous)

        Parameters:
        -----------
        pcs_to_keep: Python list of ints. len(pcs_to_keep) = num_pcs_to_keep
            Project the data onto these PCs.
            NOTE: This LIST contains indices of PCs to project the data onto, they are NOT necessarily
            contiguous.
            Example 1: [0, 2] would mean project on the 1st and 3rd largest PCs.
            Example 2: [0, 1] would mean project on the two largest PCs.

        Returns
        -----------
        pca_proj: ndarray. shape=(num_samps, num_pcs_to_keep).
            e.g. if pcs_to_keep = [0, 1],
            then pca_proj[:, 0] are x values, pca_proj[:, 1] are y values.

        NOTE: This method should set the variable `self.A_proj`
        '''
        # get original data
        # original_data = self.data.filter(items=self.vars).to_numpy()
        
        # center data
        original_data = self.A
        original_data = original_data - np.mean(original_data, axis=0)
        
        # index only the PCs to keep`
        self.A_proj =  original_data @ self.e_vecs[:, pcs_to_keep]
        
        # return projected matrix
        return self.A_proj

    def pca_then_project_back(self, top_k):
        '''Project the data into PCA space (on `top_k` PCs) then project it back to the data space

        Parameters:
        -----------
        top_k: int. Project the data onto this many top PCs.

        Returns:
        -----------
        ndarray. shape=(num_samps, num_selected_vars)

        TODO:
        - Project the data on the `top_k` PCs (assume PCA has already been performed).
        - Project this PCA-transformed data back to the original data space
        - If you normalized, remember to rescale the data projected back to the original data space.
        '''
        # first, project data from A_proj to PCA space for top_k
        self.pca_project(np.arange(0, top_k))
        
        # get P matrix
        P = self.e_vecs[:, :top_k]
        
        # consider if top_k == 1
        proj = self.A_proj
        if self.normalized:
            reconst_data = (self.ranges * (self.A_proj @ P.T)) + self.means
        else:
            reconst_data = self.A_proj @ P.T + self.means
            
        
        # return resulting data
        return reconst_data