import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("input_group19.txt");
        Scanner scanner = new Scanner(file);
        int n;
        int m;
        String[] string1;
        String[] string2;

        string1 = scanner.nextLine().split(" ");
        n = Integer.valueOf(string1[0]);
        m = Integer.valueOf(string1[1]);

        int[][] taskMatrix = new int[n][m];
        int[] startTime = new int[n];

        for (int i = 0; i < n; i++) {
            string2 = scanner.nextLine().split(" ");
            startTime[i] = Integer.valueOf(string2[0]);
            for (int j = 0; j < m; j++) {
                taskMatrix[i][j] = Integer.valueOf(string2[j + 1]);
            }
        }

        int[][] startTimes0;
        int[][] startTimes1;
        int[][] startTimes2;
        int[][] startTimes3;
        int[][] startTimes4;
        int[][] startTimes5;

        int[] indexes0;
        int[] indexes1;
        int[] indexes2;
        int[] indexes3;
        int[] indexes4;
        int[] indexes5;

        if (n > 5) {
            startTimes0 = setStartTimes(n, m, taskMatrix, startTime, 0, 1, 2);
            startTimes1 = setStartTimes(n, m, taskMatrix, startTime, 0, 2, 1);
            startTimes2 = setStartTimes(n, m, taskMatrix, startTime, 1, 0, 2);
            startTimes3 = setStartTimes(n, m, taskMatrix, startTime, 1, 2, 0);
            startTimes4 = setStartTimes(n, m, taskMatrix, startTime, 2, 1, 0);
            startTimes5 = setStartTimes(n, m, taskMatrix, startTime, 2, 0, 1);
        } else {

            indexes0 = setStartTimes2(n, m, taskMatrix, startTime, 0, 1, 2);
            indexes1 = setStartTimes2(n, m, taskMatrix, startTime, 0, 2, 1);
            indexes2 = setStartTimes2(n, m, taskMatrix, startTime, 1, 0, 2);
            indexes3 = setStartTimes2(n, m, taskMatrix, startTime, 1, 2, 0);
            indexes4 = setStartTimes2(n, m, taskMatrix, startTime, 2, 1, 0);
            indexes5 = setStartTimes2(n, m, taskMatrix, startTime, 2, 0, 1);

            startTimes0 = giveBestStartTimes(n, m, taskMatrix, startTime, 0, 1, 2, indexes0);
            startTimes1 = giveBestStartTimes(n, m, taskMatrix, startTime, 0, 2, 1, indexes1);
            startTimes2 = giveBestStartTimes(n, m, taskMatrix, startTime, 1, 0, 2, indexes2);
            startTimes3 = giveBestStartTimes(n, m, taskMatrix, startTime, 1, 2, 0, indexes3);
            startTimes4 = giveBestStartTimes(n, m, taskMatrix, startTime, 2, 1, 0, indexes4);
            startTimes5 = giveBestStartTimes(n, m, taskMatrix, startTime, 2, 0, 1, indexes5);

        }

        /*int [] ind = new int[3];
        ind[0] = 0;
        ind[1] = 0;
        ind[2] = 1;
        giveBestStartTimes(n, m, taskMatrix, startTime, 1, 2, 0, ind);*/

        int[] totalEndTimes = new int[6];

        totalEndTimes[0] = getMax(startTimes0, n);
        totalEndTimes[1] = getMax(startTimes1, n);
        totalEndTimes[2] = getMax(startTimes2, n);
        totalEndTimes[3] = getMax(startTimes3, n);
        totalEndTimes[4] = getMax(startTimes4, n);
        totalEndTimes[5] = getMax(startTimes5, n);

        int minTotalTimeIndex = getMinIndex(totalEndTimes);
        printResult(minTotalTimeIndex, totalEndTimes, startTimes0, startTimes1, startTimes2, startTimes3, startTimes4, startTimes5, n);

    }

    private static void printResult(int minTimeIndex, int[] t, int[][] res0, int[][] res1, int[][] res2,
                                    int[][] res3, int[][] res4, int[][] res5, int n) {
        System.out.println(t[minTimeIndex]);
        if (minTimeIndex == 0) {
            for (int i = 0; i < n; i++) {
                System.out.println(res0[0][i] + " " + res0[1][i] + " " + res0[2][i]);
            }
        }
        if (minTimeIndex == 1) {
            for (int i = 0; i < n; i++) {
                System.out.println(res1[0][i] + " " + res1[2][i] + " " + res1[1][i]);
            }
        }
        if (minTimeIndex == 2) {
            for (int i = 0; i < n; i++) {
                System.out.println(res2[1][i] + " " + res2[0][i] + " " + res2[2][i]);
            }
        }
        if (minTimeIndex == 3) {
            for (int i = 0; i < n; i++) {
                System.out.println(res3[2][i] + " " + res3[0][i] + " " + res3[1][i]);
            }
        }
        if (minTimeIndex == 4) {
            for (int i = 0; i < n; i++) {
                System.out.println(res4[2][i] + " " + res4[1][i] + " " + res4[0][i]);
            }
        }
        if (minTimeIndex == 5) {
            for (int i = 0; i < n; i++) {
                System.out.println(res5[1][i] + " " + res5[2][i] + " " + res5[0][i]);
            }
        }
    }

    private static int getMax(int[][] res, int n) {
        int max = res[0][n];
        if (res[1][n] > max) {
            max = res[1][n];
        }
        if (res[2][n] > max) {
            max = res[2][n];
        }
        return max;
    }

    private static int getMinIndex(int[] t) {
        int min = t[0];
        int minIndex = 0;
        for (int i = 0; i < 5; i++) {
            if (t[i] < min) {
                min = t[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private static int[][] setStartTimes(int n, int m, int[][] taskMatrix, int[] startTime, int zero, int one, int two) {

        int[] worksStartTime1 = new int[n + 1];

        Boolean[] isDone = new Boolean[n];
        for (int i = 0; i < n; i++) {
            isDone[i] = false;
        }

        int time = 0;
        int biggestWork1;
        int biggestWork2;
        int biggestWorkIndex1;
        int biggestWorkIndex2;
        int biggestWorkStartTime2;

        for (int i = 0; i < n; i++) {

            biggestWork1 = 0;
            biggestWork2 = 0;
            biggestWorkIndex1 = 0;
            biggestWorkIndex2 = 0;
            biggestWorkStartTime2 = Integer.MAX_VALUE;

            for (int j = 0; j < n; j++) {
                if (!isDone[j]) {
                    if (startTime[j] <= time) {
                        if (biggestWork1 < taskMatrix[j][zero]) {
                            biggestWork1 = taskMatrix[j][zero];
                            biggestWorkIndex1 = j;
                        }
                    } else {
                        if (biggestWork1 == 0) {
                            if (startTime[j] < biggestWorkStartTime2) {
                                biggestWorkStartTime2 = startTime[j];
                                biggestWorkIndex2 = j;
                                biggestWork2 = taskMatrix[j][zero];
                            } else {
                                if (startTime[j] == biggestWorkStartTime2) {
                                    if (biggestWork2 < taskMatrix[j][zero]) {
                                        biggestWorkIndex2 = j;
                                        biggestWork2 = taskMatrix[j][zero];
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (biggestWork1 == 0) {
                if (biggestWork2 != 0) {
                    isDone[biggestWorkIndex2] = true;
                    worksStartTime1[biggestWorkIndex2] = biggestWorkStartTime2;
                    time = biggestWorkStartTime2 + biggestWork2;
                }
            } else {
                isDone[biggestWorkIndex1] = true;
                worksStartTime1[biggestWorkIndex1] = time;
                time = time + biggestWork1;
            }
        }

        worksStartTime1[n] = time;


        int[] worksStartTime2 = new int[n + 1];
        for (int i = 0; i < n; i++) {
            isDone[i] = false;
        }

        time = 0;
        int startTimeTmp2;

        for (int i = 0; i < n; i++) {

            biggestWork1 = 0;
            biggestWork2 = 0;
            biggestWorkIndex1 = 0;
            biggestWorkIndex2 = 0;
            biggestWorkStartTime2 = Integer.MAX_VALUE;

            for (int j = 0; j < n; j++) {
                if (!isDone[j]) {
                    if (
                            ((startTime[j] <= time) && (time + taskMatrix[j][one] <= worksStartTime1[j])) ||
                                    (time >= worksStartTime1[j] + taskMatrix[j][zero])
                            ) {
                        if (biggestWork1 < taskMatrix[j][one]) {
                            biggestWork1 = taskMatrix[j][one];
                            biggestWorkIndex1 = j;
                        }
                    } else {
                        if (biggestWork1 == 0) {
                            if (startTime[j] > time) {
                                if (startTime[j] + taskMatrix[j][one] <= worksStartTime1[j]) {
                                    startTimeTmp2 = startTime[j];
                                } else {
                                    startTimeTmp2 = worksStartTime1[j] + taskMatrix[j][zero];
                                }
                            } else {
                                startTimeTmp2 = worksStartTime1[j] + taskMatrix[j][zero];
                            }
                            if (startTimeTmp2 < biggestWorkStartTime2) {
                                biggestWorkStartTime2 = startTimeTmp2;
                                biggestWorkIndex2 = j;
                                biggestWork2 = taskMatrix[j][one];
                            } else {
                                if (startTimeTmp2 == biggestWorkStartTime2) {
                                    if (biggestWork2 < taskMatrix[j][one]) {
                                        biggestWorkIndex2 = j;
                                        biggestWork2 = taskMatrix[j][one];
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (biggestWork1 == 0) {
                if (biggestWork2 != 0) {
                    isDone[biggestWorkIndex2] = true;
                    worksStartTime2[biggestWorkIndex2] = biggestWorkStartTime2;
                    time = biggestWorkStartTime2 + biggestWork2;
                }
            } else {
                isDone[biggestWorkIndex1] = true;
                worksStartTime2[biggestWorkIndex1] = time;
                time = time + biggestWork1;
            }
        }

        worksStartTime2[n] = time;


        int[] worksStartTime3 = new int[n + 1];
        for (int i = 0; i < n; i++) {
            isDone[i] = false;
        }

        time = 0;
        startTimeTmp2 = 0;

        for (int i = 0; i < n; i++) {

            biggestWork1 = 0;
            biggestWork2 = 0;
            biggestWorkIndex1 = 0;
            biggestWorkIndex2 = 0;
            biggestWorkStartTime2 = Integer.MAX_VALUE;

            for (int j = 0; j < n; j++) {
                if (!isDone[j]) {
                    if (worksStartTime1[j] < worksStartTime2[j]) {
                        if (
                                ((startTime[j] <= time) && (time + taskMatrix[j][two] <= worksStartTime1[j])) ||
                                        ((time >= worksStartTime1[j] + taskMatrix[j][zero]) && (time + taskMatrix[j][two] <= worksStartTime2[j])) ||
                                        (time >= worksStartTime2[j] + taskMatrix[j][one])
                                ) {
                            if (biggestWork1 < taskMatrix[j][two]) {
                                biggestWork1 = taskMatrix[j][two];
                                biggestWorkIndex1 = j;
                            }
                        } else {
                            if (biggestWork1 == 0) {
                                if (startTime[j] > time) {
                                    if (startTime[j] + taskMatrix[j][two] <= worksStartTime1[j]) {
                                        startTimeTmp2 = startTime[j];
                                    } else {
                                        if (worksStartTime1[j] + taskMatrix[j][zero] + taskMatrix[j][two] <= worksStartTime2[j]) {
                                            startTimeTmp2 = worksStartTime1[j] + taskMatrix[j][zero];
                                        } else {
                                            startTimeTmp2 = worksStartTime2[j] + taskMatrix[j][one];
                                        }
                                    }
                                } else {
                                    if (
                                            ((time + taskMatrix[j][two] > worksStartTime1[j])
                                                    && (time + taskMatrix[j][two] <= worksStartTime1[j] + taskMatrix[j][zero])) ||
                                                    ((time >= worksStartTime1[j]) && (time < worksStartTime1[j] + taskMatrix[j][zero]))) {
                                        if (worksStartTime1[j] + taskMatrix[j][zero] + taskMatrix[j][two] <= worksStartTime2[j]) {
                                            startTimeTmp2 = worksStartTime1[j] + taskMatrix[j][zero];
                                        } else {
                                            startTimeTmp2 = worksStartTime2[j] + taskMatrix[j][one];
                                        }
                                    } else {
                                        startTimeTmp2 = worksStartTime2[j] + taskMatrix[j][one];
                                    }
                                }
                            }
                        }
                    } else {
                        if (
                                ((startTime[j] <= time) && (time + taskMatrix[j][two] <= worksStartTime2[j])) ||
                                        ((time >= worksStartTime2[j] + taskMatrix[j][one]) && (time + taskMatrix[j][two] <= worksStartTime1[j])) ||
                                        (time >= worksStartTime1[j] + taskMatrix[j][zero])
                                ) {
                            if (biggestWork1 < taskMatrix[j][two]) {
                                biggestWork1 = taskMatrix[j][two];
                                biggestWorkIndex1 = j;
                            }
                        } else {
                            if (biggestWork1 == 0) {
                                if (startTime[j] > time) {
                                    if (startTime[j] + taskMatrix[j][two] <= worksStartTime2[j]) {
                                        startTimeTmp2 = startTime[j];
                                    } else {
                                        if (worksStartTime2[j] + taskMatrix[j][one] + taskMatrix[j][two] <= worksStartTime1[j]) {
                                            startTimeTmp2 = worksStartTime2[j] + taskMatrix[j][one];
                                        } else {
                                            startTimeTmp2 = worksStartTime1[j] + taskMatrix[j][zero];
                                        }
                                    }
                                } else {
                                    if (
                                            ((time + taskMatrix[j][two] > worksStartTime2[j])
                                                    && (time + taskMatrix[j][two] <= worksStartTime2[j] + taskMatrix[j][one])) ||
                                                    ((time >= worksStartTime2[j]) && (time < worksStartTime2[j] + taskMatrix[j][one]))) {
                                        if (worksStartTime2[j] + taskMatrix[j][one] + taskMatrix[j][two] <= worksStartTime1[j]) {
                                            startTimeTmp2 = worksStartTime2[j] + taskMatrix[j][one];
                                        } else {
                                            startTimeTmp2 = worksStartTime1[j] + taskMatrix[j][zero];
                                        }

                                    } else {
                                        startTimeTmp2 = worksStartTime1[j] + taskMatrix[j][zero];
                                    }
                                }
                            }
                        }
                    }

                    if (startTimeTmp2 < biggestWorkStartTime2) {
                        biggestWorkStartTime2 = startTimeTmp2;
                        biggestWorkIndex2 = j;
                        biggestWork2 = taskMatrix[j][two];
                    } else {
                        if (startTimeTmp2 == biggestWorkStartTime2) {
                            if (biggestWork2 < taskMatrix[j][two]) {
                                biggestWorkIndex2 = j;
                                biggestWork2 = taskMatrix[j][two];
                            }
                        }
                    }
                }
            }

            if (biggestWork1 == 0) {
                if (biggestWork2 != 0) {
                    isDone[biggestWorkIndex2] = true;
                    worksStartTime3[biggestWorkIndex2] = biggestWorkStartTime2;
                    time = biggestWorkStartTime2 + biggestWork2;
                }
            } else {
                isDone[biggestWorkIndex1] = true;
                worksStartTime3[biggestWorkIndex1] = time;
                time = time + biggestWork1;
            }
        }

        worksStartTime3[n] = time;


        int[][] workStartTimes = new int[3][n + 1];

        workStartTimes[0] = worksStartTime1;
        workStartTimes[1] = worksStartTime2;
        workStartTimes[2] = worksStartTime3;

        return workStartTimes;
    }

    private static ArrayList<String> permute(String str, int l, int r, ArrayList<String> permutations) {
        if (l == r)
            //System.out.println(str);
            permutations.add(str);
        else {
            for (int i = l; i <= r; i++) {
                str = swap(str, l, i);
                permute(str, l + 1, r, permutations);
                str = swap(str, l, i);
            }
        }
        return permutations;
    }

    private static String swap(String a, int i, int j) {
        char temp;
        char[] charArray = a.toCharArray();
        temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
    }

    private static int[] setStartTimes2(int n, int m, int[][] taskMatrix, int[] startTime, int zero, int one, int two) {

        ArrayList<String> permutations = new ArrayList<>();
        switch (n) {
            case 7:
                permutations = permute("0123456", 0, 6, permutations);
                break;
            case 6:
                permutations = permute("012345", 0, 5, permutations);
                break;
            case 5:
                permutations = permute("01234", 0, 4, permutations);
                break;
            case 4:
                permutations = permute("0123", 0, 3, permutations);
                break;
            case 3:
                permutations = permute("012", 0, 2, permutations);
                break;
            case 2:
                permutations = permute("01", 0, 1, permutations);
                break;
            case 1:
                permutations = permute("0", 0, 0, permutations);
                break;
        }

        int time;
        int bestTime = Integer.MAX_VALUE;
        int btii = 0;
        int btjj = 0;
        int btkk = 0;
        int[] worksStartTime1 = new int[n + 1];
        int[] worksStartTime2 = new int[n + 1];
        int[] worksStartTime3 = new int[n + 1];


        for (int ii = 0; ii < permutations.size(); ii++) { // on permutations
            // first work stations
            time = 0;
            for (int i = 0; i < n; i++) { // on each work
                int currentWorkIndex1 = Character.getNumericValue(permutations.get(ii).charAt(i));
                if (startTime[currentWorkIndex1] <= time) {
                    worksStartTime1[currentWorkIndex1] = time;
                    time += taskMatrix[currentWorkIndex1][zero];
                } else {
                    worksStartTime1[currentWorkIndex1] = startTime[currentWorkIndex1];
                    time = startTime[currentWorkIndex1] + taskMatrix[currentWorkIndex1][zero];
                }
            }

            worksStartTime1[n] = time;

            // second work stations
            for (int jj = 0; jj < permutations.size(); jj++) {// set second work stations time
                time = 0;
                for (int j = 0; j < n; j++) { // on each work
                    int currentWorkIndex2 = Character.getNumericValue(permutations.get(jj).charAt(j));
                    if (
                            ((startTime[currentWorkIndex2] <= time) && (time + taskMatrix[currentWorkIndex2][one] <= worksStartTime1[currentWorkIndex2])) ||
                                    (time >= worksStartTime1[currentWorkIndex2] + taskMatrix[currentWorkIndex2][zero])
                            ) {
                        worksStartTime2[currentWorkIndex2] = time;
                        time += taskMatrix[currentWorkIndex2][one];
                    } else {
                        if (startTime[currentWorkIndex2] > time) {
                            if (startTime[currentWorkIndex2] + taskMatrix[currentWorkIndex2][one] <= worksStartTime1[currentWorkIndex2]) {
                                worksStartTime2[currentWorkIndex2] = startTime[currentWorkIndex2];
                                time = startTime[currentWorkIndex2] + taskMatrix[currentWorkIndex2][one];
                            } else {
                                worksStartTime2[currentWorkIndex2] = startTime[currentWorkIndex2] + taskMatrix[currentWorkIndex2][zero];
                                time = startTime[currentWorkIndex2] + taskMatrix[currentWorkIndex2][zero] + taskMatrix[currentWorkIndex2][one];
                            }
                        } else {
                            worksStartTime2[currentWorkIndex2] = startTime[currentWorkIndex2] + taskMatrix[currentWorkIndex2][zero];
                            time = startTime[currentWorkIndex2] + taskMatrix[currentWorkIndex2][zero] + taskMatrix[currentWorkIndex2][one];
                        }
                    }
                }

                worksStartTime2[n] = time;

                // third work stations
                for (int kk = 0; kk < permutations.size(); kk++) {// set third work stations time
                    time = 0;
                    for (int k = 0; k < n; k++) {
                        int currentWorkIndex3 = Character.getNumericValue(permutations.get(kk).charAt(k));
                        if (worksStartTime1[currentWorkIndex3] < worksStartTime2[currentWorkIndex3]) {
                            if (
                                    ((startTime[currentWorkIndex3] <= time) && (time + taskMatrix[currentWorkIndex3][two] <= worksStartTime1[currentWorkIndex3])) ||
                                            ((time >= worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero]) && (time + taskMatrix[currentWorkIndex3][two] <= worksStartTime2[currentWorkIndex3])) ||
                                            (time >= worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one])
                                    ) {
                                worksStartTime3[currentWorkIndex3] = time;
                                time += taskMatrix[currentWorkIndex3][two];
                            } else {
                                if (startTime[currentWorkIndex3] > time) {
                                    if (startTime[currentWorkIndex3] + taskMatrix[currentWorkIndex3][two] <= worksStartTime1[currentWorkIndex3]) {
                                        worksStartTime3[currentWorkIndex3] = startTime[currentWorkIndex3];
                                        time = startTime[currentWorkIndex3] + taskMatrix[currentWorkIndex3][two];
                                    } else {
                                        if (worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero] + taskMatrix[currentWorkIndex3][two] <= worksStartTime2[currentWorkIndex3]) {
                                            worksStartTime3[currentWorkIndex3] = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero];
                                            time = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero] + taskMatrix[currentWorkIndex3][two];
                                        } else {
                                            worksStartTime3[currentWorkIndex3] = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one];
                                            time = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one] + taskMatrix[currentWorkIndex3][two];
                                        }
                                    }
                                } else {
                                    if (
                                            ((time + taskMatrix[currentWorkIndex3][two] > worksStartTime1[currentWorkIndex3])
                                                    && (time + taskMatrix[currentWorkIndex3][two] <= worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero])) ||
                                                    ((time >= worksStartTime1[currentWorkIndex3]) && (time < worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero]))) {
                                        if (worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero] + taskMatrix[currentWorkIndex3][two] <= worksStartTime2[currentWorkIndex3]) {
                                            worksStartTime3[currentWorkIndex3] = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero];
                                            time = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero] + taskMatrix[currentWorkIndex3][two];
                                        } else {
                                            worksStartTime3[currentWorkIndex3] = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one];
                                            time = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one] + taskMatrix[currentWorkIndex3][two];
                                        }
                                    } else {
                                        worksStartTime3[currentWorkIndex3] = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one];
                                        time = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one] + taskMatrix[currentWorkIndex3][two];
                                    }
                                }
                            }
                        } else {
                            if (
                                    ((startTime[currentWorkIndex3] <= time) && (time + taskMatrix[currentWorkIndex3][two] <= worksStartTime2[currentWorkIndex3])) ||
                                            ((time >= worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one]) && (time + taskMatrix[currentWorkIndex3][two] <= worksStartTime1[currentWorkIndex3])) ||
                                            (time >= worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero])
                                    ) {
                                worksStartTime3[currentWorkIndex3] = time;
                                time += taskMatrix[currentWorkIndex3][two];
                            } else {
                                if (startTime[currentWorkIndex3] > time) {
                                    if (startTime[currentWorkIndex3] + taskMatrix[currentWorkIndex3][two] <= worksStartTime2[currentWorkIndex3]) {
                                        worksStartTime3[currentWorkIndex3] = startTime[currentWorkIndex3];
                                        time = startTime[currentWorkIndex3] + taskMatrix[currentWorkIndex3][two];
                                    } else {
                                        if (worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one] + taskMatrix[currentWorkIndex3][two] <= worksStartTime1[currentWorkIndex3]) {
                                            worksStartTime3[currentWorkIndex3] = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one];
                                            time = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one] + taskMatrix[currentWorkIndex3][two];
                                        } else {
                                            worksStartTime3[currentWorkIndex3] = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero];
                                            time = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero] + taskMatrix[currentWorkIndex3][two];
                                        }
                                    }
                                } else {
                                    if (
                                            ((time + taskMatrix[currentWorkIndex3][two] > worksStartTime2[currentWorkIndex3])
                                                    && (time + taskMatrix[currentWorkIndex3][two] <= worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one])) ||
                                                    ((time >= worksStartTime2[currentWorkIndex3]) && (time < worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one]))) {
                                        if (worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one] + taskMatrix[currentWorkIndex3][two] <= worksStartTime1[currentWorkIndex3]) {
                                            worksStartTime3[currentWorkIndex3] = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one];
                                            time = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one] + taskMatrix[currentWorkIndex3][two];
                                        } else {
                                            worksStartTime3[currentWorkIndex3] = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero];
                                            time = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero] + taskMatrix[currentWorkIndex3][two];
                                        }

                                    } else {
                                        worksStartTime3[currentWorkIndex3] = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero];
                                        time = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero] + taskMatrix[currentWorkIndex3][two];
                                    }
                                }
                            }
                        }
                    }
                    worksStartTime3[n] = time;

                    int[][] workStartTimes = new int[3][n + 1];
                    int thisTime;

                    workStartTimes[0] = worksStartTime1;
                    workStartTimes[1] = worksStartTime2;
                    workStartTimes[2] = worksStartTime3;

                    thisTime = getMax(workStartTimes, n);
                    if (thisTime < bestTime) {
                        bestTime = thisTime;
                        btii = ii;
                        btjj = jj;
                        btkk = kk;
                    }
                }
            }
        }


        int[] indexes = new int[3];
        indexes[0] = btii;
        indexes[1] = btjj;
        indexes[2] = btkk;

        return indexes;

    }

    private static int[][] giveBestStartTimes(int n, int m, int[][] taskMatrix, int[] startTime, int zero, int one, int two, int[] indexes) {
        ArrayList<String> permutations = new ArrayList<>();
        switch (n) {
            case 7:
                permutations = permute("0123456", 0, 6, permutations);
                break;
            case 6:
                permutations = permute("012345", 0, 5, permutations);
                break;
            case 5:
                permutations = permute("01234", 0, 4, permutations);
                break;
            case 4:
                permutations = permute("0123", 0, 3, permutations);
                break;
            case 3:
                permutations = permute("012", 0, 2, permutations);
                break;
            case 2:
                permutations = permute("01", 0, 1, permutations);
                break;
            case 1:
                permutations = permute("0", 0, 0, permutations);
                break;
        }

        int time;

        int[] worksStartTime1 = new int[n + 1];
        int[] worksStartTime2 = new int[n + 1];
        int[] worksStartTime3 = new int[n + 1];


        // first work stations
        time = 0;
        for (int i = 0; i < n; i++) { // on each work
            int currentWorkIndex1 = Character.getNumericValue(permutations.get(indexes[0]).charAt(i));
            if (startTime[currentWorkIndex1] <= time) {
                worksStartTime1[currentWorkIndex1] = time;
                time += taskMatrix[currentWorkIndex1][zero];
            } else {
                worksStartTime1[currentWorkIndex1] = startTime[currentWorkIndex1];
                time = startTime[currentWorkIndex1] + taskMatrix[currentWorkIndex1][zero];
            }
        }

        worksStartTime1[n] = time;

        // second work stations
        time = 0;
        for (int j = 0; j < n; j++) { // on each work
            int currentWorkIndex2 = Character.getNumericValue(permutations.get(indexes[1]).charAt(j));
            if (
                    ((startTime[currentWorkIndex2] <= time) && (time + taskMatrix[currentWorkIndex2][one] <= worksStartTime1[currentWorkIndex2])) ||
                            (time >= worksStartTime1[currentWorkIndex2] + taskMatrix[currentWorkIndex2][zero])
                    ) {
                worksStartTime2[currentWorkIndex2] = time;
                time += taskMatrix[currentWorkIndex2][one];
            } else {
                if (startTime[currentWorkIndex2] > time) {
                    if (startTime[currentWorkIndex2] + taskMatrix[currentWorkIndex2][one] <= worksStartTime1[currentWorkIndex2]) {
                        worksStartTime2[currentWorkIndex2] = startTime[currentWorkIndex2];
                        time = startTime[currentWorkIndex2] + taskMatrix[currentWorkIndex2][one];
                    } else {
                        // errroeee e e
                        worksStartTime2[currentWorkIndex2] = worksStartTime1[currentWorkIndex2] + taskMatrix[currentWorkIndex2][zero];
                        time = worksStartTime1[currentWorkIndex2] + taskMatrix[currentWorkIndex2][zero] + taskMatrix[currentWorkIndex2][one];
                    }
                } else {
                    worksStartTime2[currentWorkIndex2] = worksStartTime1[currentWorkIndex2] + taskMatrix[currentWorkIndex2][zero];
                    time = worksStartTime1[currentWorkIndex2] + taskMatrix[currentWorkIndex2][zero] + taskMatrix[currentWorkIndex2][one];
                }
            }
        }

        worksStartTime2[n] = time;

        // third work stations
        time = 0;
        for (int k = 0; k < n; k++) {
            int currentWorkIndex3 = Character.getNumericValue(permutations.get(indexes[2]).charAt(k));
            if (worksStartTime1[currentWorkIndex3] < worksStartTime2[currentWorkIndex3]) {
                if (
                        ((startTime[currentWorkIndex3] <= time) && (time + taskMatrix[currentWorkIndex3][two] <= worksStartTime1[currentWorkIndex3])) ||
                                ((time >= worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero]) && (time + taskMatrix[currentWorkIndex3][two] <= worksStartTime2[currentWorkIndex3])) ||
                                (time >= worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one])
                        ) {
                    worksStartTime3[currentWorkIndex3] = time;
                    time += taskMatrix[currentWorkIndex3][two];
                } else {
                    if (startTime[currentWorkIndex3] > time) {
                        if (startTime[currentWorkIndex3] + taskMatrix[currentWorkIndex3][two] <= worksStartTime1[currentWorkIndex3]) {
                            worksStartTime3[currentWorkIndex3] = startTime[currentWorkIndex3];
                            time = startTime[currentWorkIndex3] + taskMatrix[currentWorkIndex3][two];
                        } else {
                            if (worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero] + taskMatrix[currentWorkIndex3][two] <= worksStartTime2[currentWorkIndex3]) {
                                worksStartTime3[currentWorkIndex3] = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero];
                                time = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero] + taskMatrix[currentWorkIndex3][two];
                            } else {
                                worksStartTime3[currentWorkIndex3] = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one];
                                time = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one] + taskMatrix[currentWorkIndex3][two];
                            }
                        }
                    } else {
                        if (
                                ((time + taskMatrix[currentWorkIndex3][two] > worksStartTime1[currentWorkIndex3])
                                        && (time + taskMatrix[currentWorkIndex3][two] <= worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero])) ||
                                        ((time >= worksStartTime1[currentWorkIndex3]) && (time < worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero]))) {
                            if (worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero] + taskMatrix[currentWorkIndex3][two] <= worksStartTime2[currentWorkIndex3]) {
                                worksStartTime3[currentWorkIndex3] = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero];
                                time = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero] + taskMatrix[currentWorkIndex3][two];
                            } else {
                                worksStartTime3[currentWorkIndex3] = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one];
                                time = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one] + taskMatrix[currentWorkIndex3][two];
                            }
                        } else {
                            worksStartTime3[currentWorkIndex3] = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one];
                            time = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one] + taskMatrix[currentWorkIndex3][two];
                        }
                    }
                }
            } else {
                if (
                        ((startTime[currentWorkIndex3] <= time) && (time + taskMatrix[currentWorkIndex3][two] <= worksStartTime2[currentWorkIndex3])) ||
                                ((time >= worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one]) && (time + taskMatrix[currentWorkIndex3][two] <= worksStartTime1[currentWorkIndex3])) ||
                                (time >= worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero])
                        ) {
                    worksStartTime3[currentWorkIndex3] = time;
                    time += taskMatrix[currentWorkIndex3][two];
                } else {
                    if (startTime[currentWorkIndex3] > time) {
                        if (startTime[currentWorkIndex3] + taskMatrix[currentWorkIndex3][two] <= worksStartTime2[currentWorkIndex3]) {
                            worksStartTime3[currentWorkIndex3] = startTime[currentWorkIndex3];
                            time = startTime[currentWorkIndex3] + taskMatrix[currentWorkIndex3][two];
                        } else {
                            if (worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one] + taskMatrix[currentWorkIndex3][two] <= worksStartTime1[currentWorkIndex3]) {
                                worksStartTime3[currentWorkIndex3] = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one];
                                time = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one] + taskMatrix[currentWorkIndex3][two];
                            } else {
                                worksStartTime3[currentWorkIndex3] = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero];
                                time = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero] + taskMatrix[currentWorkIndex3][two];
                            }
                        }
                    } else {
                        if (
                                ((time + taskMatrix[currentWorkIndex3][two] > worksStartTime2[currentWorkIndex3])
                                        && (time + taskMatrix[currentWorkIndex3][two] <= worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one])) ||
                                        ((time >= worksStartTime2[currentWorkIndex3]) && (time < worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one]))) {
                            if (worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one] + taskMatrix[currentWorkIndex3][two] <= worksStartTime1[currentWorkIndex3]) {
                                worksStartTime3[currentWorkIndex3] = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one];
                                time = worksStartTime2[currentWorkIndex3] + taskMatrix[currentWorkIndex3][one] + taskMatrix[currentWorkIndex3][two];
                            } else {
                                worksStartTime3[currentWorkIndex3] = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero];
                                time = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero] + taskMatrix[currentWorkIndex3][two];
                            }

                        } else {
                            worksStartTime3[currentWorkIndex3] = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero];
                            time = worksStartTime1[currentWorkIndex3] + taskMatrix[currentWorkIndex3][zero] + taskMatrix[currentWorkIndex3][two];
                        }
                    }

                }

            }

        }

        worksStartTime3[n] = time;

        int[][] workStartTimes = new int[3][n + 1];

        workStartTimes[0] = worksStartTime1;
        workStartTimes[1] = worksStartTime2;
        workStartTimes[2] = worksStartTime3;

        return workStartTimes;

    }
}
