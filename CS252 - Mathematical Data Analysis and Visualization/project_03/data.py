'''data.py
Reads CSV files, stores data, access/filter data by variable name
Samuel Munoz
CS 251 Data Analysis and Visualization
Spring 2021
'''

import numpy as np
import csv
import os
import sys
import copy

# For debugging purposes
from sys import argv

class Data:
    def __init__(self, filepath=None, headers=None, data=None, header2col=None):
        # intitalizing fields
        # checking if file exist; is so, self.filepath = filepath
        # if os.path.exists(filepath): 
        #     self.filepath = filepath
        # # otherwise, self.filepath = None
        # else:
        #     self.filepath = None
        self.filepath = filepath
        self.headers = headers
        self.data = data
        self.header2col = header2col

        # read data if filepath exists
        if not self.filepath == None:
           self.read(filepath) 

        '''Data object constructor

        Parameters:
        -----------
        filepath: str or None. Path to data .csv file
        headers: Python list of strings or None. List of strings that explain the name of each
            column of data.
        data: ndarray or None. shape=(N, M).
            N is the number of data samples (rows) in the dataset and M is the number of variables
            (cols) in the dataset.
            2D numpy array of the dataset values, all formatted as floats.
            NOTE: In Week 1, don't worry working with ndarrays yet. Assume it will be passed in
                  as None for now.
        header2col: Python dictionary or None.
                Maps header (var str name) to column index (int).
                Example: "sepal_length" -> 0

        TODO:
        - Declare/initialize the following instance variables:
            - filepath
            - headers
            - data
            - header2col
            - Any others you find helpful in your implementation
        - If `filepath` isn't None, call the `read` method.
        '''

    def read(self, filepath):
        '''Read in the .csv file `filepath` in 2D tabular format. Convert to numpy ndarray called
        `self.data` at the end (think of this as 2D array or table).

        Format of `self.data`:
            Rows should correspond to i-th data sample.
            Cols should correspond to j-th variable / feature.

        Parameters:
        -----------
        filepath: str or None. Path to data .csv file

        Returns:
        -----------
        None. (No return value).
            NOTE: In the future, the Returns section will be omitted from docstrings if
            there should be nothing returned

        TODO:
        - Read in the .csv file `filepath` to set `self.data`. Parse the file to only store
        numeric columns of data in a 2D tabular format (ignore non-numeric ones). Make sure
        everything that you add is a float.
        - Represent `self.data` (after parsing your CSV file) as an numpy ndarray. To do this:
            - At the top of this file write: import numpy as np
            - Add this code before this method ends: self.data = np.array(self.data)
        - Be sure to fill in the fields: `self.headers`, `self.data`, `self.header2col`.

        NOTE: You may wish to leverage Python's built-in csv module. Check out the documentation here:
        https://docs.python.org/3/library/csv.html

        NOTE: In any CS251 project, you are welcome to create as many helper methods as you'd like.
        The crucial thing is to make sure that the provided method signatures work as advertised.

        NOTE: You should only use the basic Python library to do your parsing.
        (i.e. no Numpy or imports other than csv).
        Points will be taken off otherwise.

        TIPS:
        - If you're unsure of the data format, open up one of the provided CSV files in a text editor
        or check the project website for some guidelines.
        - Check out the test scripts for the desired outputs.
        '''
        # check if file exists; if not, pass function/method
        if not os.path.exists(filepath):
            return

        # check if filepath matches self.filepath. if not, update it
        if not filepath == self.filepath:
            self.filepath = filepath

        # make self.data equal an empty list
        self.data = []

        # open the csv file and read its content
        with open(filepath, newline='') as csv_file:
            # make csv reader object
            csv_reader = csv.reader(csv_file, delimiter=',', quotechar='|')

            # read csv file and store data where need be
            self.data = []
            indices_to_remove = []
            self.headers = []
            self.header2col = {}
            
            # constant: holds strings with the names of all data types. NOTE: this is not the way I should implement this. I should seek if python has some built in way to check for data types. this is a limitation of my own python knowledge
            DATA_TYPES = ["string", "numeric", "enum", "date"]
            
            # fetch first and second row from data
            first_row = next(csv_reader)
            second_row = next(csv_reader)
            
            num_cols = 0
            # look at first and second row; populate self.headers and self.header2col
            for index in range(0, len(first_row)):
                h_data = first_row[index].strip()
                dt_data = second_row[index].strip()
                
                # check if this row contains real data types. if not, then print error message and terminate program
                if DATA_TYPES.count(dt_data) == 0:
                    print(f'ERROR: \"{dt_data}\" is not a real data type. Please make sure your data type row contains real data types.')
                    sys.exit(1)
                    
                # check if data_type is a numeric type
                if not dt_data == "numeric":
                    indices_to_remove.append(index)
                    continue

                # otherwise, data is numeric type; add to self.headers and self.header2col
                self.headers.append(h_data) # trim all entries
                self.header2col.update({h_data: num_cols})
                num_cols += 1
            
            for sample in csv_reader:
                new_sample = []
                # print(sample)
                # print(indices_to_remove)
                for index in range(0, len(sample)):
                    # if column index stores numeric data, store into new sample
                    if indices_to_remove.count(index) == 0:
                        new_sample.append(float(sample[index]))

                # store new_sample into self.data
                self.data.append(new_sample)

        # convert 2D array into ndarray
        self.data = np.array(self.data)    

        # for sample in self.data:
        #     print(sample)

    def __str__(self):
        '''toString method

        (For those who don't know, __str__ works like toString in Java...In this case, it's what's
        called to determine what gets shown when a `Data` object is printed.)

        Returns:
        -----------
        str. A nicely formatted string representation of the data in this Data object.
            Only show, at most, the 1st 5 rows of data
            See the test code for an example output.
        '''
        # typing in the first line
        rtn_string = "----------------------------------------------\n"
        if not self.filepath == None:
            rtn_string += self.filepath
        else:
            rtn_string += "--No/ge filename--"
        rtn_string += " ("
        rtn_string += str(self.data.shape[0])
        rtn_string += "x"
        rtn_string += str(self.data.shape[1])
        rtn_string += ")\n"

        # typing in next section
        rtn_string += "Headers:\n\t"
        for element in self.headers:
            rtn_string += element
            rtn_string += "\t"
        rtn_string += "\n"
        
        # checking number of samples. if len(samples) >= 5, display the first five samples. otherwise, display all samples
        end = 5
        if self.data.shape[0] < 5:
            end = self.data.shape[0] 

        # typing in the data section
        rtn_string += "----------------------------------------------\n"
        rtn_string += "Showing first "
        rtn_string += str(end)
        rtn_string += "/"
        rtn_string += str(self.data.shape[0])
        rtn_string += " rows.\n"

        for row_index in range(0, end):
            for element in self.data[row_index]:
                rtn_string += str(element)
                rtn_string += "\t"
            rtn_string += "\n"
        rtn_string += "----------------------------------------------\n"

        return rtn_string

    def get_headers(self):
        '''Get method for headers

        Returns:
        -----------
        Python list of str.
        '''
        return self.headers

    def get_mappings(self):
        '''Get method for mapping between variable name and column index

        Returns:
        -----------
        Python dictionary. str -> int
        '''
        return self.header2col

    def get_num_dims(self):
        '''Get method for number of dimensions in each data sample

        Returns:
        -----------
        int. Number of dimensions in each data sample. Same thing as number of variables.
        '''
        # if data has not been populated and stored as an ndarray, return 0
        if np.all(self.data) == None:
            return 0
        # otherwise, make sure that self.data is a ndarray
        elif isinstance(self.data, np.ndarray):
            return self.data.shape[1]

    def get_num_samples(self):
        '''Get method for number of data points (samples) in the dataset

        Returns:
        -----------
        int. Number of data samples in dataset.
        '''
        # if data has not been populated and stored as an ndarray, return 0
        if np.all(self.data) == None:
            return 0
        # otherwise, make sure that self.data is a ndarray
        elif isinstance(self.data, np.ndarray):
            return self.data.shape[0]

    def get_sample(self, rowInd):
        '''Gets the data sample at index `rowInd` (the `rowInd`-th sample)

        Returns:
        -----------
        ndarray. shape=(num_vars,) The data sample at index `rowInd`
        '''
        return self.data[rowInd]

    def get_header_indices(self, headers):
        '''Gets the variable (column) indices of the str variable names in `headers`.

        Parameters:
        -----------
        headers: Python list of str. Header names to take from self.data

        Returns:
        -----------
        Python list of nonnegative ints. shape=len(headers). The indices of the headers in `headers`
            list.
        '''
        # create return list
        return_list = []

        # loop though all variables names
        for header in headers:
            # if variable name does exists in data, then add column index to return list
            if header in self.header2col:
                return_list.append(self.header2col[header])
            # otherwise, add '-1' to list. this implies that the variable passed through does not exist
            else:
                return_list.append(-1)

        return return_list

    def get_all_data(self):
        '''Gets a copy of the entire dataset

        (Week 2)

        Returns:
        -----------
        ndarray. shape=(num_data_samps, num_vars). A copy of the entire dataset.
            NOTE: This should be a COPY, not the data stored here itself.
            This can be accomplished with numpy's copy function.
        '''
        return copy.deepcopy(self.data)  

    def head(self):
        '''Return the 1st five data samples (all variables)

        (Week 2)

        Returns:
        -----------
        ndarray. shape=(5, num_vars). 1st five data samples.
        '''
        # check size of data; if there are less than 5 data samples, get all samples
        end = 5
        if len(self.data) < 5:
            end = len(self.data)
        
        # make new list and append to it the first 5 samples
        head = [self.data[i] for i in range(0, end)]
        return np.array(head)

    def tail(self):
        '''Return the last five data samples (all variables)

        (Week 2)

        Returns:
        -----------
        ndarray. shape=(5, num_vars). Last five data samples.
        '''
        # check size of data; if there are less than 5 data samples, get all samples
        end = 5
        if len(self.data) < 5:
            end = len(self.data)
        
        # make new list with the last five data samples
        tail = [self.data[i] for i in range(-1*end, 0)]
        return np.array(tail)

    def limit_samples(self, start_row, end_row):
        '''Update the data so that this `Data` object only stores samples in the contiguous range:
            `start_row` (inclusive), end_row (exclusive)
        Samples outside the specified range are no longer stored.

        (Week 2)

        '''
        # check if start_row and end_row indices exist in data sample
        if start_row < 0 or start_row >= len(self.data):
            print(f"ERROR: start_row index {start_row} does not exist in the data set. Please enter a valid index number.")
            return
            
        if end_row < 0 or end_row > len(self.data):
            print(f"ERROR: end_row index {end_row} does not exist in the data set. Please enter a valid index number.")
            return
        
        # if indices exist, then create new sample and set it equal to self.data
        new_data = [self.data[i] for i in range(start_row, end_row)]
        self.data = np.array(new_data)

    def select_data(self, headers, rows=[]):
        '''Return data samples corresponding to the variable names in `headers`.
        If `rows` is empty, return all samples, otherwise return samples at the indices specified
        by the `rows` list.

        (Week 2)

        For example, if self.headers = ['a', 'b', 'c'] and we pass in header = 'b', we return
        column #2 of self.data. If rows is not [] (say =[0, 2, 5]), then we do the same thing,
        but only return rows 0, 2, and 5 of column #2.

        Parameters:
        -----------
            headers: Python list of str. Header names to take from self.data
            rows: Python list of int. Indices of subset of data samples to select.
                Empty list [] means take all rows

        Returns:
        -----------
        ndarray. shape=(num_data_samps, len(headers)) if rows=[]
                 shape=(len(rows), len(headers)) otherwise
            Subset of data from the variables `headers` that have row indices `rows`.

        Hint: For selecting a subset of rows from the data ndarray, check out np.ix_
        '''
        # get all selected rows. let selected_rows be a list and not a ndarray
        selected_rows = rows
        if len(selected_rows) == 0:
            selected_rows = [i for i in range(0, len(self.data))]
                     
        # get all column indices
        selected_column_indices = [self.header2col[header] for header in headers]

        # return data
        return self.data[np.ix_(selected_rows, selected_column_indices)]

if __name__ == "__main__":
    d0 = Data(argv[1])
    # print(d0)
    # print(d0.get_headers())
    # print(d0.get_mappings())
    # print(d0.get_num_dims())
    # print(d0.get_num_samples())
    # print(d0.get_sample())
    # print(d0.get_headers())





####### SOURCES #########
# https://stackoverflow.com/questions/36200413/python-file-does-not-exist-exception
# https://docs.python.org/3/library/csv.html
# https://www.guru99.com/learn-python-main-function-with-examples-understand-main.html
# https://www.pythonforbeginners.com/system/python-sys-argv
# https://python-reference.readthedocs.io/en/latest/docs/dict/
# https://www.askpython.com/python/string/trim-a-string-in-python
# https://www.w3schools.com/python/python_iterators.asp
# https://realpython.com/copying-python-objects/
# https://pythonguides.com/python-check-if-the-variable-is-an-integer/

######### OLD CODE ##################
#             # print(self.headers)
#             # print(self.header2col)
#             
#             # if row holds data type information, then store column indices that hold non-numeric values
#             col = 0
#             num_pops = 0
#             
#             for dt in sample:
#                 data_type = dt.strip()
#                 # check if this row contains real data types. if not, then print error message and terminate program
#                 if DATA_TYPES.count(data_type) == 0:
#                     print(f'ERROR: \"{data_type}\" is not a real data type. Please make sure your data type row contains real data types.')
#                     
#                 # check if data_type is a numeric type
#                 if not data_type == "numeric":
#                     indices_to_remove.append(col)
#                 col += 1
#             # print(indices
#             # remove non-numeric headers
#             for index in indices_to_remove:
#                 key = self.headers.pop(index-num_pops)
#                 self.header2col.pop(key)
#                 num_pops += 1

            # if row is the header row, then store row in self.header and create self.header2col
#             for index in sample:
#                 data = element.strip()
#                 self.headers.append(data) # trim all entries
#                 self.header2col.update({data: col})
#                 col += 1
#                 
