'''linear_regression.py
Subclass of Analysis that performs linear regression on data
Samuel Munoz
CS 252 Data Analysis Visualization
Spring 2021
'''
import numpy as np
import scipy.linalg
import matplotlib.pyplot as plt

import analysis


class LinearRegression(analysis.Analysis):
    '''
    Perform and store linear regression and related analyses
    '''

    def __init__(self, data):
        '''

        Parameters:
        -----------
        data: Data object. Contains all data samples and variables in a dataset.
        '''
        super().__init__(data)

        # ind_vars: Python list of strings.
        #   1+ Independent variables (predictors) entered in the regression.
        self.ind_vars = None
        # dep_var: string. Dependent variable predicted by the regression.
        self.dep_var = None

        # A: ndarray. shape=(num_data_samps, num_ind_vars)
        #   Matrix for independent (predictor) variables in linear regression
        self.A = None

        # y: ndarray. shape=(num_data_samps, 1)
        #   Vector for dependent variable predictions from linear regression
        self.y = None

        # R2: float. R^2 statistic
        self.R2 = None

        # Mean SEE. float. Measure of quality of fit
        self.m_sse = None

        # slope: ndarray. shape=(num_ind_vars, 1)
        #   Regression slope(s)
        self.slope = None
        # intercept: float. Regression intercept
        self.intercept = None
        # residuals: ndarray. shape=(num_data_samps, 1)
        #   Residuals from regression fit
        self.residuals = None

        # p: int. Polynomial degree of regression model (Week 2)
        self.p = 1

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
        
    def linear_regression(self, ind_vars, dep_var, method='scipy', p=1):
        '''Performs a linear regression on the independent (predictor) variable(s) `ind_vars`
        and dependent variable `dep_var` using the method `method`.

        Parameters:
        -----------
        ind_vars: Python list of strings. 1+ independent variables (predictors) entered in the regression.
            Variable names must match those used in the `self.data` object.
        dep_var: str. 1 dependent variable entered into the regression.
            Variable name must match one of those used in the `self.data` object.
        method: str. Method used to compute the linear regression. Here are the options:
            'scipy': Use scipy's linregress function.
            'normal': Use normal equations.
            'qr': Use QR factorization (linear algebra section only).

        TODO:
        - Use your data object to select the variable columns associated with the independent and
        dependent variable strings.
        - Perform linear regression using the appropriate method.
        - Compute R^2 on the fit and the residuals.
        - By the end of this method, all instance variables should be set (see constructor).

        NOTE: Use other methods in this class where ever possible (do not write the same code twice!)
        '''
        # first populate the fields of this class
        self.ind_vars = ind_vars
        self.dep_var = dep_var
        
        # get matrix of only ind_vars
        self.A = self.data.select_data(ind_vars)
        coord = np.ones( (self.A.shape[0], 1) )
        A_coord = np.hstack( (coord, self.A) )
        
        # get column of dep_var
        self.y = self.data.select_data([dep_var])
        
        # call linear_regression_scipy
        if method == "scipy":
            c, _ = self.linear_regression_scipy(A_coord, self.y)
            self.intercept = c[0][0, 0]
            self.slope = c[0][1:]
        # call linear_regression_normal
        elif method == "normal":
            c = self.linear_regression_normal(A_coord, self.y)
            self.intercept = c[0]
            self.slope = c[1:]
        # call linear_regression_qr
        elif method == "qr":
            c = self.linear_regression_qr(A_coord, self.y)
            self.intercept = c[0]
            self.slope = c[1:]
        
        # set values
        self.residuals = self.compute_residuals(self.predict())
        self.R2 = self.r_squared(self.predict())
        self.m_sse = self.mean_sse()
        
    def linear_regression_scipy(self, A, y):
        '''Performs a linear regression using scipy's built-in least squares solver (scipy.linalg.lstsq).
        Solves the equation y = Ac for the coefficient vector c.

        Parameters:
        -----------
        A: ndarray. shape=(num_data_samps, num_ind_vars).
            Data matrix for independent variables.
        y: ndarray. shape=(num_data_samps, 1).
            Data column for dependent variable.

        Returns
        -----------
        c: ndarray. shape=(num_ind_vars+1,)
            Linear regression slope coefficients for each independent var PLUS the intercept term
        '''
        # compute linear regression
        c = scipy.linalg.lstsq(A, y)
        
        # return linear regression slope coefficients
        return c, c[0]

    def linear_regression_normal(self, A, y):
        '''Performs a linear regression using the normal equations.
        Solves the equation y = Ac for the coefficient vector c.

        See notebook for a refresher on the equation

        Parameters:
        -----------
        A: ndarray. shape=(num_data_samps, num_ind_vars).
            Data matrix for independent variables.
        y: ndarray. shape=(num_data_samps, 1).
            Data column for dependent variable.

        Returns
        -----------
        c: ndarray. shape=(num_ind_vars+1,)
            Linear regression slope coefficients for each independent var AND the intercept term
        '''
        # first compute A.T @ y
        c = A.T @ y
        
        # compute square matrix A.T @ A
        K = A.T @ A
        
        # since K is square, it as an inverse; compute inverse using scipy utils
        K_inverse = scipy.linalg.inv(K)
        
        # compose K_inverse with c to get answer
        c = K_inverse @ c
        
        return c

    def linear_regression_qr(self, A, y):
        '''Performs a linear regression using the QR decomposition

        (Week 2)

        See notebook for a refresher on the equation

        Parameters:
        -----------
        A: ndarray. shape=(num_data_samps, num_ind_vars).
            Data matrix for independent variables.
        y: ndarray. shape=(num_data_samps, 1).
            Data column for dependent variable.

        Returns
        -----------
        c: ndarray. shape=(num_ind_vars+1,)
            Linear regression slope coefficients for each independent var AND the intercept term

        NOTE: You should not compute any matrix inverses! Check out scipy.linalg.solve_triangular
        to backsubsitute to solve for the regression coefficients `c`.
        '''
        # decompose A
        Q, R = self.qr_decomposition(A)
        
        # use scipy.linalg.solve_triangular to find c
        c = scipy.linalg.solve_triangular(R, Q.T @ self.y)
        
        # return c array
        return c

    def qr_decomposition(self, A):
        '''Performs a QR decomposition on the matrix A. Make column vectors orthogonal relative
        to each other. Uses the Gram–Schmidt algorithm

        (Week 2)

        Parameters:
        -----------
        A: ndarray. shape=(num_data_samps, num_ind_vars+1).
            Data matrix for independent variables.

        Returns:
        -----------
        Q: ndarray. shape=(num_data_samps, num_ind_vars+1)
            Orthonormal matrix (columns are orthogonal unit vectors — i.e. length = 1)
        R: ndarray. shape=(num_ind_vars+1, num_ind_vars+1)
            Upper triangular matrix

        TODO:
        - Q is found by the Gram–Schmidt orthogonalizing algorithm.
        Summary: Step thru columns of A left-to-right. You are making each newly visited column
        orthogonal to all the previous ones. You do this by projecting the current column onto each
        of the previous ones and subtracting each projection from the current column.
            - NOTE: Very important: Make sure that you make a COPY of your current column before
            subtracting (otherwise you might modify data in A!).
        Normalize each current column after orthogonalizing.
        - R is found by equation summarized in notebook
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

    def predict(self, X=None):
        '''Use fitted linear regression model to predict the values of data matrix self.A.
        Generates the predictions y_pred = mA + b, where (m, b) are the model fit slope and intercept,
        A is the data matrix.

        Parameters:
        -----------
        X: ndarray. shape=(num_data_samps, num_ind_vars).
            If None, use self.A for the "x values" when making predictions.
            If not None, use X as independent var data as "x values" used in making predictions.

        Returns
        y_pred: ndarray. shape=(num_data_samps, 1)
            Predicted y (dependent variable) values

        NOTE: You can write this method without any loops!
        '''
        # get copy of the correct input
        if X is None:
            ind = self.A.copy()
            
        else:
            ind = X.copy()
            
        # if self.p > 1, then run make_polynomial_matrix on ind
        if self.p > 1 and ind.shape[1] == 1:
            ind = self.make_polynomial_matrix(ind, self.p)
            
        # return predicted values
        return ind @ self.slope + self.intercept
               
    def r_squared(self, y_pred):
        '''Computes the R^2 quality of fit statistic

        Parameters:
        -----------
        y_pred: ndarray. shape=(num_data_samps,).
            Dependent variable values predicted by the linear regression model
        Returns:
        -----------
        R2: float.
            The R^2 statistic
        '''
        # compute y_mean of actual y data
        y_mean = np.mean(self.y)
        
        # compute E (equation from notebook)
        # E = np.sum([(self.y[i] - y_pred[i])**2 for i in range(0, self.y.shape[0])])
        E = np.sum((self.y - y_pred)**2)
        
        # compute S (equation from notebook)
        # S = np.sum([(self.y[i] - y_mean)**2 for i in range(0, self.y.shape[0])])
        S = np.sum((self.y - y_mean)**2)
        
        # return R^2
        return 1 - E/S

    def compute_residuals(self, y_pred):
        '''Determines the residual values from the linear regression model

        Parameters:
        -----------
        y_pred: ndarray. shape=(num_data_samps, 1).
            Data column for model predicted dependent variable values.

        Returns
        -----------
        residuals: ndarray. shape=(num_data_samps, 1)
            Difference between the y values and the ones predicted by the regression model at the
            data samples
        '''
        # computes residuals
        return self.y - y_pred

    def mean_sse(self):
        '''Computes the mean sum-of-squares error in the predicted y compared the actual y values.
        See notebook for equation.

        Returns:
        -----------
        float. Mean sum-of-squares error

        Hint: Make use of self.compute_residuals
        '''
        # gets residuals
        residuals = self.compute_residuals(self.predict())
        
        # return 1 over sum of square of all residuals 
        return 1 / residuals.size * np.sum(residuals**2)
        

    def scatter(self, ind_var, dep_var, title):
        '''Creates a scatter plot with a regression line to visualize the model fit.
        Assumes linear regression has been already run.

        Parameters:
        -----------
        ind_var: string. Independent variable name
        dep_var: string. Dependent variable name
        title: string. Title for the plot

        TODO:
        - Use your scatter() in Analysis to handle the plotting of points. Note that it returns
        the (x, y) coordinates of the points.
        - Sample evenly spaced x values for the regression line between the min and max x data values
        - Use your regression slope, intercept, and x sample points to solve for the y values on the
        regression line.
        - Plot the line on top of the scatterplot.
        - Make sure that your plot has a title (with R^2 value in it)
        '''
        # get data and plot scatter plot
        x_data, y_data = super().scatter(ind_var, dep_var, title)
        
        # good amount of evenly spaced x values
        x = np.linspace(np.min(x_data), np.max(x_data), 100)
        y = self.predict(x[:, np.newaxis])
        
        # plot data
        plt.plot(x, y, '-')
        plt.xlabel(ind_var)
        plt.ylabel(dep_var)

    def pair_plot(self, data_vars, fig_sz=(12, 12), hists_on_diag=True):
        '''Makes a pair plot with regression lines in each panel.
        There should be a len(data_vars) x len(data_vars) grid of plots, show all variable pairs
        on x and y axes.

        Parameters:
        -----------
        data_vars: Python list of strings. Variable names in self.data to include in the pair plot.
        fig_sz: tuple. len(fig_sz)=2. Width and height of the whole pair plot figure.
            This is useful to change if your pair plot looks enormous or tiny in your notebook!
        hists_on_diag: bool. If true, draw a histogram of the variable along main diagonal of
            pairplot.

        TODO:
        - Use your pair_plot() in Analysis to take care of making the grid of scatter plots.
        Note that this method returns the figure and axes array that you will need to superimpose
        the regression lines on each subplot panel.
        - In each subpanel, plot a regression line of the ind and dep variable. Follow the approach
        that you used for self.scatter. Note that here you will need to fit a new regression for
        every ind and dep variable pair.
        - Make sure that each plot has a title (with R^2 value in it)
        '''
        fig, axes = super().pair_plot(data_vars, fig_sz)

        # iterate through all subplots
        for r in range(0, len(data_vars)):
            for c in range(0, len(data_vars)):
                # boolean that checks if subplot is on is on diagonal
                isDiagonal = False
                
                # value is only modified if hists_on_diag is true
                # otherwise, diagonals print regression line
                if hists_on_diag == True:
                    if r == c:
                        isDiagonal = True
                
                # run regression for pair
                self.linear_regression([data_vars[c]], data_vars[r])
                
                # add histogram is keyword is True and on a diagonal
                if hists_on_diag == True and isDiagonal == True:
                    # source code given to us in notebook
                    numVars = len(data_vars)
                    axes[r, c].remove()
                    axes[r, c] = fig.add_subplot(numVars, numVars, r*numVars + c + 1)
                    if c < numVars-1:
                        axes[r, c].set_xticks([])
                    else:
                        axes[r, c].set_xlabel(data_vars[r])
                    if r > 0:
                        axes[r, c].set_yticks([])
                    else:
                        axes[r, c].set_ylabel(data_vars[r])
                    
                    # fetch and plot data
                    data = self.data.select_data([data_vars[r]])
                    axes[r, c].hist(data)
                    
                    # give title
                    plt.title("R^2 = " + str(self.R2)[:5])
                
                # produce regression line
                if not isDiagonal:
                    # get evenly spaced x
                    x = np.linspace(np.min(self.data.data[:, c]), np.max(self.data.data[:, c]), self.y.shape[0])
                    y = self.predict(x[:, np.newaxis])
                    
                    # plot data on correct subplot
                    plt.subplot(len(data_vars), len(data_vars), r*len(data_vars) + c + 1)
                    plt.plot(x, y, '-')
                    plt.title("R^2 = " + str(self.R2)[:5])
                
                
        
    def make_polynomial_matrix(self, A, p):
        '''Takes an independent variable data column vector `A and transforms it into a matrix appropriate
        for a polynomial regression model of degree `p`.

        (Week 2)

        Parameters:
        -----------
        A: ndarray. shape=(num_data_samps, 1)
            Independent variable data column vector x
        p: int. Degree of polynomial regression model.

        Returns:
        -----------
        ndarray. shape=(num_data_samps, p)
            Independent variable data transformed for polynomial model.
            Example: if p=10, then the model should have terms in your regression model for
            x^1, x^2, ..., x^9, x^10.

        NOTE: There should not be a intercept term ("x^0"), the linear regression solver method
        will take care of that.
        '''
        # get first column of xs
        A_new = A
        
        # run for loop and multiply all xs by correct power
        for i in range(2, p+1):
            A_new = np.hstack( ( A_new, A**i ) )
            
        # return new matrix
        return A_new

    def poly_regression(self, ind_var, dep_var, p):
        '''Perform polynomial regression — generalizes self.linear_regression to polynomial curves
        (Week 2)
        NOTE: For single linear regression only (one independent variable only)

        Parameters:
        -----------
        ind_var: str. Independent variable entered in the single regression.
            Variable names must match those used in the `self.data` object.
        dep_var: str. Dependent variable entered into the regression.
            Variable name must match one of those used in the `self.data` object.
        p: int. Degree of polynomial regression model.
             Example: if p=10, then the model should have terms in your regression model for
             x^1, x^2, ..., x^9, x^10, and a column of homogeneous coordinates (1s).

        TODO:
        - This method should mirror the structure of self.linear_regression (compute all the same things)
        - Differences are:
            - You create a matrix based on the independent variable data matrix (self.A) with columns
            appropriate for polynomial regresssion. Do this with self.make_polynomial_matrix.
            - You set the instance variable for the polynomial regression degree (self.p)
        '''
        # first populate the fields of this class
        self.ind_vars = [ind_var]
        self.dep_var = dep_var
        
        # get matrix of only ind_vars
        self.A = self.data.select_data(ind_var)
        A_coord = self.make_polynomial_matrix(self.A, p)
        coord = np.ones( (A_coord.shape[0], 1) )
        A_coord = np.hstack( (coord, A_coord) )
        
        # get column of dep_var
        self.y = self.data.select_data([dep_var])
        
        # run regression
        c = self.linear_regression_qr(A_coord, self.y)
        self.intercept = c[0]
        self.slope = c[1:]
        
        # set values
        self.p = p
        self.residuals = self.compute_residuals(self.predict())
        self.R2 = self.r_squared(self.predict())
        self.m_sse = self.mean_sse()

    def get_fitted_slope(self):
        '''Returns the fitted regression slope.
        (Week 2)

        Returns:
        -----------
        ndarray. shape=(num_ind_vars, 1). The fitted regression slope(s).
        '''
        return self.slope

    def get_fitted_intercept(self):
        '''Returns the fitted regression intercept.
        (Week 2)

        Returns:
        -----------
        float. The fitted regression intercept(s).
        '''
        return self.intercept

    def initialize(self, ind_vars, dep_var, slope, intercept, p):
        '''Sets fields based on parameter values.
        (Week 2)

        Parameters:
        -----------
        ind_var: str. Independent variable entered in the single regression.
            Variable names must match those used in the `self.data` object.
        dep_var: str. Dependent variable entered into the regression.
            Variable name must match one of those used in the `self.data` object.
        slope: ndarray. shape=(num_ind_vars, 1)
            Slope coefficients for the linear regression fits for each independent var
        intercept: float.
            Intercept for the linear regression fit
        p: int. Degree of polynomial regression model.

        TODO:
        - Use parameters and call methods to set all instance variables defined in constructor. 
        '''
        # populate fields with new values
        self.ind_vars = [ind_vars]
        self.dep_var = dep_var
        self.slope = slope
        self.intercept = intercept
        self.p = p
        
        # populate self.A and self.y
        self.A = self.data.select_data(self.ind_vars)
        self.y = self.data.select_data([self.dep_var])

import data
if __name__ == "__main__":
    pass