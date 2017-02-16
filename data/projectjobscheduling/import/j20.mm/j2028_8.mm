************************************************************************
file with basedata            : md348_.bas
initial value random generator: 7210686
************************************************************************
projects                      :  1
jobs (incl. supersource/sink ):  22
horizon                       :  163
RESOURCES
  - renewable                 :  2   R
  - nonrenewable              :  2   N
  - doubly constrained        :  0   D
************************************************************************
PROJECT INFORMATION:
pronr.  #jobs rel.date duedate tardcost  MPM-Time
    1     20      0       17       14       17
************************************************************************
PRECEDENCE RELATIONS:
jobnr.    #modes  #successors   successors
   1        1          3           2   3   4
   2        3          3           8  15  16
   3        3          3           5   6  13
   4        3          2          11  21
   5        3          3           9  10  12
   6        3          3           7   9  17
   7        3          2          16  18
   8        3          2          13  14
   9        3          1          15
  10        3          1          17
  11        3          2          12  15
  12        3          2          14  18
  13        3          3          19  20  21
  14        3          2          17  19
  15        3          1          18
  16        3          1          21
  17        3          1          20
  18        3          2          19  20
  19        3          1          22
  20        3          1          22
  21        3          1          22
  22        1          0        
************************************************************************
REQUESTS/DURATIONS:
jobnr. mode duration  R 1  R 2  N 1  N 2
------------------------------------------------------------------------
  1      1     0       0    0    0    0
  2      1     1       0    7    0   10
         2     4       4    0    0    6
         3    10       0    3    1    0
  3      1     2       8    0    9    0
         2     4       7    0    9    0
         3     7       5    0    0    7
  4      1     3       8    0    0    7
         2     5       2    0    0    4
         3     8       0    5   10    0
  5      1     3       0    4    8    0
         2     4       3    0    0    9
         3     4       0    4    5    0
  6      1     5       2    0    0    3
         2     8       2    0    6    0
         3    10       0    3    5    0
  7      1     3       8    0    0    4
         2     4       6    0    3    0
         3     8       2    0    2    0
  8      1     2       4    0    0    6
         2     8       3    0    4    0
         3     8       0    9    9    0
  9      1     1       6    0    4    0
         2     6       3    0    1    0
         3    10       0    1    0    5
 10      1     5       8    0    7    0
         2     6       0    2    4    0
         3     9       7    0    1    0
 11      1     1       0    6    5    0
         2     4       0    2    0    8
         3     9       6    0    0    6
 12      1     4      10    0    0    3
         2     7       0    6    0    2
         3    10       5    0    3    0
 13      1     8       0    9    8    0
         2     8       0    9    0    2
         3    10       0    8    9    0
 14      1     1       0    9    6    0
         2     2      10    0    0    9
         3     7       0    5    0    7
 15      1     2       9    0    0   10
         2     4       7    0    0    9
         3     4       0    7    7    0
 16      1     4       0    7    0   10
         2     5       0    7    0    9
         3     8       0    5    0    8
 17      1     5       0    4    0    5
         2     5       3    0    0    7
         3     5       2    0    0    9
 18      1     5       0    8    7    0
         2     7       7    0    2    0
         3     9       6    0    0    9
 19      1     1       0    7    5    0
         2     9       0    5    3    0
         3    10       0    4    0    7
 20      1     2       0    5    0    5
         2     4       6    0    0    5
         3     7       5    0    7    0
 21      1     1       0    8    2    0
         2     5       0    5    0    7
         3    10       0    2    1    0
 22      1     0       0    0    0    0
************************************************************************
RESOURCEAVAILABILITIES:
  R 1  R 2  N 1  N 2
   28   26  108  130
************************************************************************
