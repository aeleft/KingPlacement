I cam across this problem when I participated in facebook's programming challenge. The challenge was simple: you have 2 hours to solve a timed programming question in the language of your choice.bYou are also provided with test cases.
The problem is as follows: 

You have an N x N chessboard and you wish to place N kings on it. Each row and column should contain exactly one king, and no two kings should attack each other (two kings attack each other if they are present in squares which share a corner).

The kings in the first K rows of the board have already been placed. You are given the positions of these kings as an array pos[ ]. pos[i] is the column in which the king in the ith row has already been placed. All indices are 0-indexed. In how many ways can the remaining kings be placed?

Input:
Each test case contains N and K on the first line, followed by a line having K integers, denoting the array pos[ ] as described above.

Output:
Output the number of ways to place kings in the remaining rows satisfying the above conditions.

Constraints:
1 <= N <= 16
0 <= K <= N
0 <= pos_i < N
The kings specified in the input will be in different columns and not attack each other.
