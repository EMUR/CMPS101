import java.io.*;
import java.util.Scanner;

/**
 * Created by eyad on 10/22/16.
 */
public class Sparse {

    public static void main(String[] args) throws IOException {

        if (args.length != 2)
            System.err.println("You must pass two arguments. The input file name and the output file name.");

        String inputFileName = String.format("%s",args[0]);
        String outputFileName = String.format("%s",args[1]);

        Scanner in = new Scanner(new File(inputFileName));

        int matrixSize = in.nextInt();
        Matrix A = new Matrix(matrixSize);
        Matrix B = new Matrix(matrixSize);
        int nnzA = in.nextInt();
        int nnzB = in.nextInt();

        while (in.hasNext())
        {
            for(int i = 0; i < nnzA; i++)
            {
                int row = in.nextInt();
                int col = in.nextInt();
                double val = in.nextDouble();
                A.changeEntry(row,col,val);
            }

            for(int i = 0; i < nnzB; i++)
            {
                int row = in.nextInt();
                int col = in.nextInt();
                double val = in.nextDouble();
                B.changeEntry(row,col,val);
            }

        }

        PrintWriter out = new PrintWriter(outputFileName);
        out.println("A has "+ nnzA +" non-zero entries:");
        out.println(A);
        out.println("B has "+ nnzB +" non-zero entries:");
        out.println(B);
        out.println("(1.5)*A =");
        out.println(A.scalarMult(1.5));
        out.println("A+B =");
        out.println(A.add(B));
        out.println("A+A =");
        out.println(A.add(A));
        out.println("B-A =");
        out.println(B.sub(A));
        out.println("A-A =");
        out.println(A.sub(A));
        out.println("Transpose(A) =");
        out.println(A.transpose());
        out.println("A*B =");
        out.println(A.mult(B));
        out.println("B*B =");
        out.println(B.mult(B));
        out.close();
    }
}
