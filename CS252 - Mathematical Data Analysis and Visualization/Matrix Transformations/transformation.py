'''transformation.py
Perform projections, translations, rotations, and scaling operations on Numpy ndarray data.
Samuel Munoz
CS 251 Data Analysis Visualization, Spring 2021
'''
import numpy as np
import matplotlib.pyplot as plt
import palettable
import analysis
import data


class Transformation(analysis.Analysis):

    def __init__(self, orig_dataset, data=None):
        '''Constructor for a Transformation object

        Parameters:
        -----------
        orig_dataset: Data object. shape=(N, num_vars).
            Contains the original dataset (only containing all the numeric variables,
            `num_vars` in total).
        data: Data object (or None). shape=(N, num_proj_vars).
            Contains all the data samples as the original, but ONLY A SUBSET of the variables.
            (`num_proj_vars` in total). `num_proj_vars` <= `num_vars`

        TODO:
        - Pass `data` to the superclass constructor.
        - Create an instance variable for `orig_dataset`.
        '''
        # create Analysis object
        super().__init__(data)
        
        # store orginial data
        self.orig_data = orig_dataset

    def project(self, headers):
        '''Project the original dataset onto the list of data variables specified by `headers`,
        i.e. select a subset of the variables from the original dataset.
        In other words, your goal is to populate the instance variable `self.data`.

        Parameters:
        -----------
        headers: Python list of str. len(headers) = `num_proj_vars`, usually 1-3 (inclusive), but
            there could be more.
            A list of headers (strings) specifying the feature to be projected onto each axis.
            For example: if headers = ['hi', 'there', 'cs251'], then the data variables
                'hi' becomes the 'x' variable,
                'there' becomes the 'y' variable,
                'cs251' becomes the 'z' variable.
            The length of the list matches the number of dimensions onto which the dataset is
            projected — having 'y' and 'z' variables is optional.

        TODO:
        - Create a new `Data` object that you assign to `self.data` (project data onto the `headers`
        variables). Determine and fill in 'valid' values for all the `Data` constructor
        keyword arguments (except you dont need `filepath` because it is not relevant here).
        '''
        # use select data to get new data
        project_data = self.orig_data.select_data(headers)
        
        # create header2col dictionary for new data
        new_header2col = {}
        for i in range(0, len(headers)):
            new_header2col[headers[i]] = i
        
        # reassign project_data to self.data by creating new Data object
        self.data = data.Data(data=project_data, headers=headers, header2col=new_header2col)
        

    def get_data_homogeneous(self):
        '''Helper method to get a version of the projected data array with an added homogeneous
        coordinate. Useful for homogeneous transformations.

        Returns:
        -----------
        ndarray. shape=(N, num_proj_vars+1). The projected data array with an added 'fake variable'
        column of ones on the right-hand side.
            For example: If we have the data SAMPLE (just one row) in the projected data array:
            [3.3, 5.0, 2.0], this sample would become [3.3, 5.0, 2.0, 1] in the returned array.

        NOTE:
        - Do NOT update self.data with the homogenous coordinate.
        '''
        h_coord = np.ones( (self.data.data.shape[0], 1) )
        
        return np.hstack( (self.data.get_all_data(), h_coord) )

    def translation_matrix(self, magnitudes):
        ''' Make an M-dimensional homogeneous transformation matrix for translation,
        where M is the number of features in the projected dataset.

        Parameters:
        -----------
        magnitudes: Python list of float.
            Translate corresponding variables in `headers` (in the projected dataset) by these
            amounts.

        Returns:
        -----------
        ndarray. shape=(num_proj_vars+1, num_proj_vars+1). The transformation matrix.

        NOTE: This method just creates the translation matrix. It does NOT actually PERFORM the
        translation!
        '''
        # create matrix with homogenous coordinates
        t_matrix = np.eye(self.data.data.shape[1]+1)
        
        # modify entries in matrix such that you get the correct matrix
        for i in range(0, len(magnitudes)):
            t_matrix[i, t_matrix.shape[1]-1] = magnitudes[i]
        
        # return translation matrix
        return t_matrix

    def scale_matrix(self, magnitudes):
        '''Make an M-dimensional homogeneous scaling matrix for scaling, where M is the number of
        variables in the projected dataset.

        Parameters:
        -----------
        magnitudes: Python list of float.
            Scale corresponding variables in `headers` (in the projected dataset) by these amounts.

        Returns:
        -----------
        ndarray. shape=(num_proj_vars+1, num_proj_vars+1). The scaling matrix.

        NOTE: This method just creates the scaling matrix. It does NOT actually PERFORM the scaling!
        '''
        # create matrix with homogenous coordinates
        s_matrix = np.eye(self.data.data.shape[1]+1)
        
        # modify entries in matrix such that you get the correct matrix
        for i in range(0, len(magnitudes)):
            s_matrix[i, i] = magnitudes[i]
        
        # return translation matrix
        return s_matrix

    def translate(self, magnitudes):
        '''Translates the variables `headers` in projected dataset in corresponding amounts specified
        by `magnitudes`.

        Parameters:
        -----------
        magnitudes: Python list of float.
            Translate corresponding variables in `headers` (in the projected dataset) by these amounts.

        Returns:
        -----------
        ndarray. shape=(N, num_proj_vars). The translated data (with all variables in the projected).
            dataset. NOTE: There should be NO homogenous coordinate!

        TODO:
        - Use matrix multiplication to translate the projected dataset, as advertised above.
        - Update `self.data` with a NEW Data object with the SAME `headers` and `header2col`
        dictionary as the current `self.data`, but DIFFERENT data (set to the data you
        transformed in this method). NOTE: The updated `self.data` SHOULD NOT have a homogenous
        coordinate!
        '''
        # generate translation matrix
        t_matrix = self.translation_matrix(magnitudes)
        
        # generate data with homogenous coordinate
        ones = np.ones( (self.data.data.shape[0], 1) )
        self.data.data = np.hstack( (self.data.data, ones) )
        
        # generate translated data
        self.data.data = (t_matrix @ self.data.data.T).T
        
        # remove homogenous column
        self.data.data = np.delete(self.data.data, self.data.data.shape[1]-1, 1)
        
    def scale(self, magnitudes):
        '''Scales the variables `headers` in projected dataset in corresponding amounts specified
        by `magnitudes`.

        Parameters:
        -----------
        magnitudes: Python list of float.
            Scale corresponding variables in `headers` (in the projected dataset) by these amounts.

        Returns:
        -----------
        ndarray. shape=(N, num_proj_vars). The scaled data (with all variables in the projected).
            dataset. NOTE: There should be NO homogenous coordinate!

        TODO:
        - Use matrix multiplication to scale the projected dataset, as advertised above.
        - Update `self.data` with a NEW Data object with the SAME `headers` and `header2col`
        dictionary as the current `self.data`, but DIFFERENT data (set to the data you
        transformed in this method). NOTE: The updated `self.data` SHOULD NOT have a
        homogenous coordinate!
        '''
        # generate scaled matrix
        s_matrix = self.scale_matrix(magnitudes)
        
        # generate data with homogenous coordinate
        ones = np.ones( (self.data.data.shape[0], 1) )
        self.data.data = np.hstack( (self.data.data, ones) )
        
        # generate scaled data
        self.data.data = (s_matrix @ self.data.data.T).T
        
        # remove homogenous column
        self.data.data = np.delete(self.data.data, self.data.data.shape[1]-1, 1)

    def transform(self, C):
        '''Transforms the PROJECTED dataset by applying the homogeneous transformation matrix `C`.

        Parameters:
        -----------
        C: ndarray. shape=(num_proj_vars+1, num_proj_vars+1).
            A homogeneous transformation matrix.

        Returns:
        -----------
        ndarray. shape=(N, num_proj_vars). The projected dataset after it has been transformed by `C`

        TODO:
        - Use matrix multiplication to apply the compound transformation matix `C` to the projected
        dataset.
        - Update `self.data` with a NEW Data object with the SAME `headers` and `header2col`
        dictionary as the current `self.data`, but DIFFERENT data (set to the data you
        transformed in this method). NOTE: The updated `self.data` SHOULD NOT have a homogenous
        coordinate!
        '''
        # generate data with homogenous coordinate
        ones = np.ones( (self.data.data.shape[0], 1) )
        self.data.data = np.hstack( (self.data.data, ones) )
        
        # generate scaled data
        self.data.data = (C @ self.data.data.T).T
        
        # remove homogenous column
        self.data.data = np.delete(self.data.data, self.data.data.shape[1]-1, 1)
        
    def normalize_together(self):
        '''Normalize all variables in the projected dataset together by translating the global minimum
        (across all variables) to zero and scaling the global range (across all variables) to one.

        You should normalize (update) the data stored in `self.data`.

        Returns:
        -----------
        ndarray. shape=(N, num_proj_vars). The normalized version of the projected dataset.

        NOTE: Given the goal of this project, for full credit you should implement the normalization
        using matrix multiplications (matrix transformations).
        '''
        # determine minimum and maximuum value
        minimum = np.min(self.data.data)
        maximum = np.max(self.data.data)
        
        # create compound matrix
        C = self.translation_matrix([-minimum for i in range(0, self.data.data.shape[1])])
        C = self.scale_matrix([1/(maximum-minimum) for i in range(0, self.data.data.shape[1])]) @ C
        
        # generate data with homogenous coordinate
        ones = np.ones( (self.data.data.shape[0], 1) )
        self.data.data = np.hstack( (self.data.data, ones) )
        
        # normalize data
        self.data.data = (C @ self.data.data.T).T

        # remove homogenous column
        self.data.data = np.delete(self.data.data, self.data.data.shape[1]-1, 1)
        
    def normalize_separately(self):
        '''Normalize each variable separately by translating its local minimum to zero and scaling
        its local range to one.

        You should normalize (update) the data stored in `self.data`.

        Returns:
        -----------
        ndarray. shape=(N, num_proj_vars). The normalized version of the projected dataset.

        NOTE: Given the goal of this project, for full credit you should implement the normalization
        using matrix multiplications (matrix transformations).
        '''
        # determine minimum and maximuum value
        minimum = np.min(self.data.data, axis=0)
        maximum = np.max(self.data.data, axis=0)
        
        # create compound matrix
        C = self.translation_matrix([-minimum[i] for i in range(0, self.data.data.shape[1])])
        C = self.scale_matrix([1/(maximum[i]-minimum[i]) for i in range(0, self.data.data.shape[1])]) @ C
        
        # generate data with homogenous coordinate
        ones = np.ones( (self.data.data.shape[0], 1) )
        self.data.data = np.hstack( (self.data.data, ones) )
        
        # normalize data
        self.data.data = (C @ self.data.data.T).T

        # remove homogenous column
        self.data.data = np.delete(self.data.data, self.data.data.shape[1]-1, 1)

    def rotation_matrix_3d(self, header, degrees):
        '''Make an 3-D homogeneous rotation matrix for rotating the projected data
        about the ONE axis/variable `header`.

        Parameters:
        -----------
        header: str. Specifies the variable about which the projected dataset should be rotated.
        degrees: float. Angle (in degrees) by which the projected dataset should be rotated.

        Returns:
        -----------
        ndarray. shape=(4, 4). The 3D rotation matrix with homogenous coordinate.

        NOTE: This method just creates the rotation matrix. It does NOT actually PERFORM the rotation!
        '''
        # create rotation matrix
        r_matrix = np.eye(4)
        
        # determine what index the header argument is in
        rot_index = self.data.headers.index(header)
        
        # convert degree of rotation into radians
        rotation_rad = degrees * np.pi / 180
        
        # if rotating around first variable
        if rot_index == 0:
            r_matrix[1, 1] = np.cos(rotation_rad)
            r_matrix[1, 2] = -np.sin(rotation_rad)
            r_matrix[2, 1] = np.sin(rotation_rad)
            r_matrix[2, 2] = np.cos(rotation_rad)

        # if rotating around second variable
        if rot_index == 1:
            r_matrix[0, 0] = np.cos(rotation_rad)
            r_matrix[0, 2] = np.sin(rotation_rad)
            r_matrix[2, 0] = -np.sin(rotation_rad)
            r_matrix[2, 2] = np.cos(rotation_rad)

        # if rotating around second variable
        if rot_index == 2:
            r_matrix[0, 0] = np.cos(rotation_rad)
            r_matrix[0, 1] = -np.sin(rotation_rad)
            r_matrix[1, 0] = np.sin(rotation_rad)
            r_matrix[1, 1] = np.cos(rotation_rad)

        # return rotation matrix
        return r_matrix
        
    def rotate_3d(self, header, degrees):
        '''Rotates the projected data about the variable `header` by the angle (in degrees)
        `degrees`.

        Parameters:
        -----------
        header: str. Specifies the variable about which the projected dataset should be rotated.
        degrees: float. Angle (in degrees) by which the projected dataset should be rotated.

        Returns:
        -----------
        ndarray. shape=(N, num_proj_vars). The rotated data (with all variables in the projected).
            dataset. NOTE: There should be NO homogenous coordinate!

        TODO:
        - Use matrix multiplication to rotate the projected dataset, as advertised above.
        - Update `self.data` with a NEW Data object with the SAME `headers` and `header2col`
        dictionary as the current `self.data`, but DIFFERENT data (set to the data you
        transformed in this method). NOTE: The updated `self.data` SHOULD NOT have a
        homogenous coordinate!
        '''
        # generate scaled matrix
        r_matrix = self.rotation_matrix_3d(header, degrees)
        
        # generate data with homogenous coordinate
        ones = np.ones( (self.data.data.shape[0], 1) )
        self.data.data = np.hstack( (self.data.data, ones) )
        
        # generate scaled data
        self.data.data = (r_matrix @ self.data.data.T).T
        
        # remove homogenous column
        self.data.data = np.delete(self.data.data, self.data.data.shape[1]-1, 1)

    def scatter_color(self, ind_var, dep_var, c_var, title=None):
        '''Creates a 2D scatter plot with a color scale representing the 3rd dimension.

        Parameters:
        -----------
        ind_var: str. Header of the variable that will be plotted along the X axis.
        dep_var: Header of the variable that will be plotted along the Y axis.
        c_var: Header of the variable that will be plotted along the color axis.
            NOTE: Use a ColorBrewer color palette (e.g. from the `palettable` library).
        title: str or None. Optional title that will appear at the top of the figure.
        '''
        # get colors from palettable
        # brewer_colors = palettable.colorbrewer.sequential.Greys_7.mpl_colormap
        brewer_colors = palettable.colorbrewer.qualitative.Set2_3.mpl_colormap
        
        # plot data on scatter plot
        scat = plt.scatter(self.data.select_data([ind_var]), self.data.select_data([dep_var]), c=self.data.select_data([c_var]),  cmap=brewer_colors) # edgecolors=brewer_colors[5]
        
        # show colorbar and label
        plt.colorbar(scat, label=c_var)
        
        # show xlabel
        plt.xlabel(ind_var)
        
        # show ylabel
        plt.ylabel(dep_var)
        
        # show title if title not None
        if not title == None:
            plt.title(title)

    def heatmap(self, headers=None, title=None, cmap="gray"):
        '''Generates a heatmap of the specified variables (defaults to all). Each variable is normalized
        separately and represented as its own row. Each individual is represented as its own column.
        Normalizing each variable separately means that one color axis can be used to represent all
        variables, 0.0 to 1.0.

        Parameters:
        -----------
        headers: Python list of str (or None). (Optional) The variables to include in the heatmap.
            Defaults to all variables if no list provided.
        title: str. (Optional) The figure title. Defaults to an empty string (no title will be displayed).
        cmap: str. The colormap string to apply to the heatmap. Defaults to grayscale
            -- black (0.0) to white (1.0)

        Returns:
        -----------
        fig, ax: references to the figure and axes on which the heatmap has been plotted
        '''

        # Create a doppelganger of this Transformation object so that self.data
        # remains unmodified when heatmap is done
        data_clone = data.Data(headers=self.data.get_headers(),
                               data=self.data.get_all_data(),
                               header2col=self.data.get_mappings())
        dopp = Transformation(self.data, data_clone)
        dopp.normalize_separately()

        fig, ax = plt.subplots()
        if title is not None:
            ax.set_title(title)
        ax.set(xlabel="Individuals")

        # Select features to plot
        if headers is None:
            headers = dopp.data.headers
        m = dopp.data.select_data(headers)

        # Generate heatmap
        hmap = ax.imshow(m.T, aspect="auto", cmap=cmap, interpolation='None')

        # Label the features (rows) along the Y axis
        y_lbl_coords = np.arange(m.shape[1]+1) - 0.5
        ax.set_yticks(y_lbl_coords, minor=True)
        y_lbls = [""] + headers
        ax.set_yticklabels(y_lbls)
        ax.grid(linestyle='none')

        # Create and label the colorbar
        cbar = fig.colorbar(hmap)
        cbar.ax.set_ylabel("Normalized Features")

        return fig, ax

    def normalize_zscore_together(self):
        '''Normalize all variables in the projected dataset together by translating the global minimum
        (across all variables) to zero and scaling the global range (across all variables) to one.

        You should normalize (update) the data stored in `self.data`.

        Returns:
        -----------
        ndarray. shape=(N, num_proj_vars). The normalized version of the projected dataset.

        NOTE: Given the goal of this project, for full credit you should implement the normalization
        using matrix multiplications (matrix transformations).
        '''
        # make local variable to store dataset (shallow copy)
        cpy_data = self.data.data
        
        # determine mean and standard deviation
        mean = np.mean(cpy_data) 
        std_dev = np.std(cpy_data)
        # print(mean, "\n", mean/std_dev)
        
        # create compound matrix
        C = self.translation_matrix([-mean for i in range(0, self.data.data.shape[1])])
        C = self.scale_matrix([1/std_dev for i in range(0, self.data.data.shape[1])]) @ C
        # print(C)
        
        # generate data with homogenous coordinate
        ones = np.ones( (self.data.data.shape[0], 1) )
        self.data.data = np.hstack( (self.data.data, ones) )
        
        # normalize data with z-score
        self.data.data = (C @ self.data.data.T).T
 
        # remove homogenous column
        self.data.data = np.delete(self.data.data, self.data.data.shape[1]-1, 1)

    def normalize_zscore_seperately(self):
        '''Normalize all variables in the projected dataset together by translating the global minimum
        (across all variables) to zero and scaling the global range (across all variables) to one.

        You should normalize (update) the data stored in `self.data`.

        Returns:
        -----------
        ndarray. shape=(N, num_proj_vars). The normalized version of the projected dataset.

        NOTE: Given the goal of this project, for full credit you should implement the normalization
        using matrix multiplications (matrix transformations).
        '''
        # make local variable to store dataset (shallow copy)
        cpy_data = self.data.data
        
        # determine mean and standard deviation
        mean = np.mean(cpy_data, axis=0) 
        std_dev = np.std(cpy_data, axis=0)
        # print(mean, "\n", mean/std_dev)
        
        # create compound matrix
        C = self.translation_matrix([-mean[i] for i in range(0, self.data.data.shape[1])])
        C = self.scale_matrix([1/std_dev[i] for i in range(0, self.data.data.shape[1])]) @ C
        # print(C)
        
        # generate data with homogenous coordinate
        ones = np.ones( (self.data.data.shape[0], 1) )
        self.data.data = np.hstack( (self.data.data, ones) )
        
        # normalize data with z-score
        self.data.data = (C @ self.data.data.T).T
 
        # remove homogenous column
        self.data.data = np.delete(self.data.data, self.data.data.shape[1]-1, 1)