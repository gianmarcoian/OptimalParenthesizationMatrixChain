/***************************************************************
Student Name: Gianmarco Iannario
File Name: DPSolutionMC.java
Assignment number: Project1

File that contains the main class.
Here I will do  the work for all the different stages of the project's requirements
***************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;




public class DPSolutionMC {
	public static final int MAX_NUMBER_OF_MATRICES = 10;

	public static void main(String[] args) {	   
		int[] dimensions = readMatrixDimensions("matrixDimensions.txt");    
        int n = dimensions.length - 1;
        System.out.println("n: " + n);


        int[][] m = new int[n+1][n+1];
        int[][] s = new int[n+1][n+1];

        //calling this method will automatically print the matrix M and S at each iteration of the algorithm
        matrixChainOrder(dimensions, n, m, s);

        // print final parenthesization
        System.out.println("Optimal Parenthesization:");
        printOptimalParenthesization(s, 1, n);

        //------------end of part 2 of the project requirements-------------------

        //------------start of part 3---------------------------------------------
        System.out.println("\n\n");
        System.out.println("PART 3: ");
        System.out.println();

        // Print results
        System.out.println("Optimal Parenthesization:");
        printOptimalParenthesization(s, 1, n);

        // Print postfix expression
        System.out.println("\nPostfix Expression:");
        printPostfixExpression(s, 1, n);

        // Print matrix dimensions
        printMatrixDimensions(dimensions);
        System.out.println("\n\n");

        //------------end of part 3 of the project requirements-------------------

        //------------start of part 4 and 5---------------------------------------------
        System.out.println("\n\n");
        System.out.println("PART 4 and 5: SEE THE FILE (Result.txt) ");
        System.out.println();

        int[][] m1 = new int[n+1][n+1];
        int[][] s1 = new int[n+1][n+1];
        int[][] m2 = new int[n+1][n+1];
        int[][] s2 = new int[n+1][n+1];
        
        worstResult(dimensions, n, m1, s1);
        bestResult(dimensions, n, m2, s2);

        // now i need to create the file result.txt not in the currect directory, but in the parent one
        writeResultsToFile("results.txt", "Worst Parenthesization:", s1, m1, n,false);
        writeResultsToFile("results.txt", "Optimal Parenthesization:", s2, m2, n,true);

	}
	
	
	//this is the dynamic programming solution of the matrix chain order algorithm
    //in this implementation I am currently printing the matrices M and S, so 
    //I will use this method only for PART 2 of the project
	public static void matrixChainOrder(int[] p, int n, int[][] m, int[][] s) {
        for (int i = 1; i <= n; i++) {
            m[i][i] = 0;
        }
        for (int i = 1; i <= n; i++) {
        	for (int j = 1; i <= n; i++) {
        		s[i][j] = 0;
        	}
        }

        for (int L = 2; L <= n; L++) {
            for (int i = 1; i <= n - L + 1; i++) {
                int j = i + L - 1;
                m[i][j] = Integer.MAX_VALUE;

                for (int k = i; k <= j - 1; k++) {
                    int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];

                    if (q < m[i][j]) {
                        m[i][j] = q;
                        s[i][j - 1] = k;
                    }
                    System.out.println("i="+i+"\nj="+j);
                    //System.out.println("\n\n\n");
                    System.out.println("k="+k);
                    System.out.println("M matrix:");
                    for(int t1=1; t1<=n;t1++) {
                    	for(int t2=1; t2<=n;t2++) {
                            System.out.print(m[t1][t2]+"\t");
                    		}
                        System.out.println("\n");
                    	}
                    System.out.println("\n");

        			System.out.println("S matrix:");
                	for(int t1=1; t1<=n;t1++) {
                		for(int t2=0; t2<n;t2++) {
                			System.out.print(s[t1][t2]+"\t");
                			}
                		System.out.println("\n");
                		}
                		
                	}
                	
                }
            }
		}

    //this is the function that gives us the worst case of parenthesization 
    public static void worstResult(int[] p, int n, int[][] m, int[][] s) {
        
        for (int i = 1; i <= n; i++) {
            m[i][i] = 0;
        }

        for (int L = 2; L <= n; L++) {
            for (int i = 1; i <= n - L + 1; i++) {
                int j = i + L - 1;
                m[i][j] = Integer.MIN_VALUE;

                for (int k = i; k <= j - 1; k++) {
                    int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];

                    if (q > m[i][j]) {
                        m[i][j] = q;
                        s[i][j - 1] = k;
                    }
                }
            }
        }
    }

    //this is a copy of the original Dynamic Programming solution, without printing(for Part 2 request)
    public static void bestResult(int[] p, int n, int[][] m, int[][] s) {

        for (int i = 1; i <= n; i++) {
            m[i][i] = 0;
        }
        for (int L = 2; L <= n; L++) {
            for (int i = 1; i <= n - L + 1; i++) {
                int j = i + L - 1;
                m[i][j] = Integer.MAX_VALUE;

                for (int k = i; k <= j - 1; k++) {
                    int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];

                    if (q < m[i][j]) {
                        m[i][j] = q;
                        s[i][j - 1] = k;
                    }
                }
            }
        }
    }
    
    // Method to read the dimensions of the matrices from a file
    private static int[] readMatrixDimensions(String fileName) {
    ArrayList<Integer> dimensionsList = new ArrayList<>();
    try {
        String fileEndPoint = System.getProperty("user.dir") + File.separator + fileName;
        Scanner scanner = new Scanner(new File(fileEndPoint));
        
        while (scanner.hasNextInt()) {
            int dimension = scanner.nextInt();
            dimensionsList.add(dimension);
            System.out.println("Dimensione: " + dimension);
        }

        scanner.close();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }

    // Converti l'ArrayList in un array di interi
    int[] dimensionsArray = new int[dimensionsList.size()];
    for (int i = 0; i < dimensionsList.size(); i++) {
        dimensionsArray[i] = dimensionsList.get(i);
    }

    return dimensionsArray;
}
	
	//method to print the optimal parenthesization, given s and the last element of the first row
	public static void printOptimalParenthesization(int[][] s, int i, int j) {
	    if (i == j) {
	        System.out.print("A" + i);
	    } else {
	        System.out.print("(");
	        printOptimalParenthesization(s, i, s[i][j - 1]);
	        System.out.print("*");
	        printOptimalParenthesization(s, s[i][j - 1] + 1, j);
	        System.out.print(")");
	    }
	}

    //method to print the postfix expression, I use this for part 3
    public static void printPostfixExpression(int[][] s, int i, int j) {
        if (i == j) {
            System.out.print("A" + i);
        } else {
            printPostfixExpression(s, i, s[i][j - 1]);
            printPostfixExpression(s, s[i][j - 1] + 1, j);
            System.out.print("*");
        }
    }

    //as the requirements asks for part 3, i have this method to print the dimension of the matrices
    public static void printMatrixDimensions(int[] dimensions) {
        for (int i = 0; i < dimensions.length-1; i++) {
        	System.out.println();
        	System.out.print("matrix " + (i + 1) + ": " + dimensions[i] + " x " + dimensions[i + 1]);
        }
    }

    //same implementation of the printParenthesization method, but this time I write to the results.txt 
    //and not in the terminal
    public static void printParenthesizationToFile(BufferedWriter bw, int[][] s, int i, int j) throws IOException {
        if (i == j) {
            bw.write("A" + i);
        } else {
            bw.write("(");
            printParenthesizationToFile(bw, s, i, s[i][j - 1]);
            bw.write("*");
            printParenthesizationToFile(bw, s, s[i][j - 1] + 1, j);
            bw.write(")");
        }
    }
    //same implementation of the printPostfixExpression method, but this time I write to the results.txt 
    //and not in the terminal
    public static void printPostfixExpressionToFile(BufferedWriter bw,int[][] s, int i, int j)  throws IOException {
        if (i == j) {
        	bw.write("A" + i);
        } else {
        	printPostfixExpressionToFile(bw,s, i, s[i][j - 1]);
        	printPostfixExpressionToFile(bw,s, s[i][j - 1] + 1, j);
        	bw.write("*");
        }
    }

//method to write on Results.txt file. It contains a boolean append, because i want only the first 
//invocation to this method to overwrite the all file(already existent), meanwhile the second invocation will 
//append the content on the already existent content
public static void writeResultsToFile(String fileName, String title, int[][] s, int[][] m, int n, boolean append) {
    try {
        // getting the absolute path of my java class
        String javaFilePath = DPSolutionMC.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        File javaFile = new File(javaFilePath);

        // getting the parent path of the current path (in which ther is the java class)
        String parentDirectoryPath = javaFile.getParent();
    
        // building the file path in the parent path of the current one
        String outputFilePath = parentDirectoryPath + File.separator + fileName;

        FileWriter fw = new FileWriter(outputFilePath,append);
        BufferedWriter bw = new BufferedWriter(fw);

        // Write title
        bw.write(title + ":\n");

        // Write Parenthesization
        bw.write("Parenthesization:\n");
        printParenthesizationToFile(bw, s, 1, n);
        bw.write("\n");

        // Write Postfix Expression
        bw.write("Postfix Expression:\n");
        printPostfixExpressionToFile(bw, s, 1, n);
        bw.write("\n");

        // Write Number of scalar multiplications
        bw.write("Number of scalar multiplications needed: " + m[1][n] + "\n\n");

        // Close the writer
        bw.close();
    } catch (IOException | URISyntaxException e) {
        e.printStackTrace();
    }
}



}
	




