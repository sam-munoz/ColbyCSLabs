'''analysis.py
Run statistical analyses and plot Numpy ndarray data
Samuel Munoz
CS 251 Data Analysis Visualization, Spring 2021
'''
import numpy as np
import matplotlib.pyplot as plt


class Analysis:
    def __init__(self, data):
        '''

        Parameters:
        -----------
        data: Data object. Contains all data samples and variables in a dataset.
        '''
        self.data = data

        # Make plot font sizes legible
        plt.rcParams.update({'font.size': 18})

    # have questions??? ask
    def set_data(self, data):
        '''Method that re-assigns the instance variable `data` with the parameter.
        Convenience method to change the data used in an analysis without having to create a new
        Analysis object.

        Parameters:
        -----------
        data: Data object. Contains all data samples and variables in a dataset.
        '''
        self.data = data

    def min(self, headers, rows=[]):
        '''Computes the minimum of each variable in `headers` in the data object.
        Possibly only in a subset of data samples (`rows`) if `rows` is not empty.
        (i.e. the minimum value in each of the selected columns)

        Parameters:
        -----------
        headers: Python list of str.
            One str per header variable name in data
        rows: Python list of int.
            Indices of data samples to restrict computation of min over, or over all indices
            if rows=[]

        Returns
        -----------
        mins: ndarray. shape=(len(headers),)
            Minimum values for each of the selected header variables

        NOTE: Loops are forbidden!
        '''
        # get selected row
        selected_rows = rows
        # if row is empty, then select all rows
        if len(rows) == 0:
            selected_rows = [i for i in range(0, self.data.data.shape[0])]
            
        # get selected column indices
        selected_column_indices = [self.data.header2col[key] for key in headers]
        
        # get selected data
        selected_data = self.data.data[np.ix_(selected_rows, selected_column_indices)]

        # find minumum
        return np.min(selected_data, axis=0)
        
    def max(self, headers, rows=[]):
        '''Computes the maximum of each variable in `headers` in the data object.
        Possibly only in a subset of data samples (`rows`) if `rows` is not empty.

        Parameters:
        -----------
        headers: Python list of str.
            One str per header variable name in data
        rows: Python list of int.
            Indices of data samples to restrict computation of max over, or over all indices
            if rows=[]

        Returns
        -----------
        maxs: ndarray. shape=(len(headers),)
            Maximum values for each of the selected header variables

        NOTE: Loops are forbidden!
        '''
        # get selected row
        selected_rows = rows
        # if row is empty, then select all rows
        if len(rows) == 0:
            selected_rows = [i for i in range(0, self.data.data.shape[0])]
            
        # get selected column indices
        selected_column_indices = [self.data.header2col[key] for key in headers]
        
        # get selected data
        selected_data = self.data.data[np.ix_(selected_rows, selected_column_indices)]

        # find minumum
        return np.max(selected_data, axis=0)
        
    def range(self, headers, rows=[]):
        '''Computes the range [min, max] for each variable in `headers` in the data object.
        Possibly only in a subset of data samples (`rows`) if `rows` is not empty.

        Parameters:
        -----------
        headers: Python list of str.
            One str per header variable name in data
        rows: Python list of int.
            Indices of data samples to restrict computation of min/max over, or over all indices
            if rows=[]

        Returns
        -----------
        mins: ndarray. shape=(len(headers),)
            Minimum values for each of the selected header variables
        maxes: ndarray. shape=(len(headers),)
            Maximum values for each of the selected header variables

        NOTE: Loops are forbidden!
        '''
        # get minimum list
        min_list = self.min(headers, rows)
        
        # get maximum list
        max_list = self.max(headers, rows)
        
        # subtract the lists to get range
        return min_list, max_list 

    def mean(self, headers, rows=[]):
        '''Computes the mean for each variable in `headers` in the data object.
        Possibly only in a subset of data samples (`rows`).

        Parameters:
        -----------
        headers: Python list of str.
            One str per header variable name in data
        rows: Python list of int.
            Indices of data samples to restrict computation of mean over, or over all indices
            if rows=[]

        Returns
        -----------
        means: ndarray. shape=(len(headers),)
            Mean values for each of the selected header variables

        NOTE: You CANNOT use np.mean here!
        NOTE: Loops are forbidden!
        '''
        # get selected row
        selected_rows = rows
        # if row is empty, then select all rows
        if len(rows) == 0:
            selected_rows = [i for i in range(0, self.data.data.shape[0])]
            
        # get selected column indices
        selected_column_indices = [self.data.header2col[key] for key in headers]

        # get selected data
        selected_data = self.data.data[np.ix_(selected_rows, selected_column_indices)]
        
        # find sum of each column and divide by the number elements per column
        return np.sum(selected_data, axis=0) / selected_data.shape[0]
        
    def var(self, headers, rows=[]):
        '''Computes the variance for each variable in `headers` in the data object.
        Possibly only in a subset of data samples (`rows`) if `rows` is not empty.

        Parameters:
        -----------
        headers: Python list of str.
            One str per header variable name in data
        rows: Python list of int.
            Indices of data samples to restrict computation of variance over, or over all indices
            if rows=[]

        Returns
        -----------
        vars: ndarray. shape=(len(headers),)
            Variance values for each of the selected header variables

        NOTE: You CANNOT use np.var or np.mean here!
        NOTE: Loops are forbidden!
        '''
        # get selected row
        selected_rows = rows
        # if row is empty, then select all rows
        if len(rows) == 0:
            selected_rows = [i for i in range(0, self.data.data.shape[0])]
            
        # get selected column indices
        selected_column_indices = [self.data.header2col[key] for key in headers]

        # get selected data
        selected_data = self.data.data[np.ix_(selected_rows, selected_column_indices)]
        
        # get mean
        mean = self.mean(headers, rows)
        
        # find sum of each column and divide by the number elements per column
        return np.sum( (selected_data - mean)**2, axis=0) / (selected_data.shape[0]-1)

    def std(self, headers, rows=[]):
        '''Computes the standard deviation for each variable in `headers` in the data object.
        Possibly only in a subset of data samples (`rows`) if `rows` is not empty.

        Parameters:
        -----------
        headers: Python list of str.
            One str per header variable name in data
        rows: Python list of int.
            Indices of data samples to restrict computation of standard deviation over,
            or over all indices if rows=[]

        Returns
        -----------
        vars: ndarray. shape=(len(headers),)
            Standard deviation values for each of the selected header variables

        NOTE: You CANNOT use np.var, np.std, or np.mean here!
        NOTE: Loops are forbidden!
        '''
        # get selected row
        selected_rows = rows
        # if row is empty, then select all rows
        if len(rows) == 0:
            selected_rows = [i for i in range(0, self.data.data.shape[0])]
            
        # get selected column indices
        selected_column_indices = [self.data.header2col[key] for key in headers]

        # get selected data
        selected_data = self.data.data[np.ix_(selected_rows, selected_column_indices)]
        
        # get mean
        mean = self.mean(headers, rows)
        
        # find sum of each column and divide by the number elements per column
        return (np.sum( (selected_data - mean)**2, axis=0) / (selected_data.shape[0]-1))**(1/2)

    def show(self):
        '''Simple wrapper function for matplotlib's show function.

        (Does not require modification)
        '''
        plt.show()

    def scatter(self, ind_var, dep_var, title):
        '''Creates a simple scatter plot with "x" variable in the dataset `ind_var` and
        "y" variable in the dataset `dep_var`. Both `ind_var` and `dep_var` should be strings
        in `self.headers`.

        Parameters:
        -----------
        ind_var: str.
            Name of variable that is plotted along the x axis
        dep_var: str.
            Name of variable that is plotted along the y axis
        title: str.
            Title of the scatter plot

        Returns:
        -----------
        x. ndarray. shape=(num_data_samps,)
            The x values that appear in the scatter plot
        y. ndarray. shape=(num_data_samps,)
            The y values that appear in the scatter plot

        NOTE: Do not call plt.show() here.
        '''
        # get values from self.data.data
        x_list = np.squeeze(self.data.select_data([ind_var]))
        y_list = np.squeeze(self.data.select_data([dep_var]))
        
        # place title
        plt.title(title)
        
        # plot the data
        plt.plot(x_list, y_list, "o")
        
        # return the x- and y-lists
        return x_list, y_list
        

    def pair_plot(self, data_vars, fig_sz=(12, 12), title=''):
        '''Create a pair plot: grid of scatter plots showing all combinations of variables in
        `data_vars` in the x and y axes.

        Parameters:
        -----------
        data_vars: Python list of str.
            Variables to place on either the x or y axis of the scatter plots
        fig_sz: tuple of 2 ints.
            The width and height of the figure of subplots. Pass as a parameter to plt.subplots.
        title. str. Title for entire figure (not the individual subplots)

        Returns:
        -----------
        fig. The matplotlib figure.
            1st item returned by plt.subplots
        axes. ndarray of AxesSubplot objects. shape=(len(data_vars), len(data_vars))
            2nd item returned by plt.subplots

        TODO:
        - Make the len(data_vars) x len(data_vars) grid of scatterplots
        - The y axis of the first column should be labeled with the appropriate variable being
        plotted there.
        - The x axis of the last row should be labeled with the appropriate variable being plotted
        there.
        - There should be no other axis or tick labels (it looks too cluttered otherwise!)

        Tip: Check out the sharex and sharey keyword arguments of plt.subplots.
        Because variables may have different ranges, pair plot columns usually share the same
        x axis and rows usually share the same y axis.
        '''
        # create figure with len(data_vars) x len(data_vars) subplots
        fig, axes = plt.subplots(len(data_vars), len(data_vars), figsize=fig_sz)
        
        # set title for figure
        fig.suptitle(title)
        
        # loop through data_vars twice and plot each variable as needed
        for row_index in range(0, len(data_vars)):
            for col_index in range(0, len(data_vars)):
                # display index numbers
#                 print("row: ", row_index, "col: ", col_index)
                
                # get x- and y-list
                x = np.squeeze(self.data.select_data([data_vars[row_index]]))
                y = np.squeeze(self.data.select_data([data_vars[col_index]]))
                
                # create subplot for an axes
                axes[row_index, col_index] = plt.subplot(len(data_vars), len(data_vars), (row_index)*len(data_vars) + col_index+1)
                
                # removes xticks and yticks
                # uncomment 
                # axes[row_index, col_index].set_xticks([])
                # axes[row_index, col_index].set_yticks([])

                # plot data
                plt.plot(x, y, ".")
                
                # add xlabel and ylabel
                plt.xlabel(data_vars[row_index])
                plt.ylabel(data_vars[col_index])
                
        # adjust spacing between subplots
        fig.subplots_adjust(wspace=0.6, hspace=0.6)

        # return figure and plots
        return fig, axes