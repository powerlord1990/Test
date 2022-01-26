package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        if(inputNumbers.contains(null)) throw new CannotBuildPyramidException();

        int row = getRowNumber(inputNumbers);

        if(row == -1) throw new CannotBuildPyramidException();

        int col = 2 * row - 1;
        int[][] pyramid = new int[row][col];
        Collections.sort(inputNumbers);
        Queue<Integer> queue = new LinkedList<>(inputNumbers);
        int startPosition = (pyramid[0].length) / 2;
        for(int i = 0; i < pyramid.length; i++)
        {
            int start = startPosition;
            for(int j = 0; j <= i; j++)
            {
                pyramid[i][start] = queue.remove();
                start += 2;
            }
            startPosition --;
        }
        return pyramid;
    }

    private static int getRowNumber(List<Integer> input)
    {
        int	listSize = input.size();
        double result = (Math.sqrt(1+ 8 * listSize) - 1)/2;
        if(result == Math.ceil(result)) return (int)result;
        return -1;
    }
}
