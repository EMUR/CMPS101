/**
 * Created by eyad on 10/28/16.
 */
public class MatrixTest {
    public static void main(String[] args) {
        int size = 10;
        Matrix A = new Matrix(size);
        Matrix B = new Matrix(size);

        for(int i = 1; i<=size; i++)
        {
            for(int j = 1; j<=size; j++)
            {
                A.changeEntry(i,j,size-j);
                B.changeEntry(i,j,j);
            }
        }

        System.out.println("A has "+ A.getNNZ() +" non-zero entries:");
        System.out.println(A);
        System.out.println("B has "+ B.getNNZ() +" non-zero entries:");
        System. out.println(B);
        System. out.println("(1.5)*A =");
        System. out.println(A.scalarMult(1.5));
        System. out.println("A+B =");
        System. out.println(A.add(B));
        System.out.println("A+A =");
        System.out.println(A.add(A));
        System.out.println("B-A =");
        System.out.println(B.sub(A));
        System.out.println("A-A =");
        System.out.println(A.sub(A));
        System.out.println("Transpose(A) =");
        System. out.println(A.transpose());
        System. out.println("A*B =");
        System. out.println(A.mult(B));
        System. out.println("B*B =");
        System. out.println(B.mult(B));

        System. out.println("C is a copy of A");
        Matrix C = A.copy();
        System.out.println("C has "+ C.getNNZ() +" non-zero entries:");
        System.out.println(C);
        System.out.println("Does C equal to A?");
        System.out.println(A.equals(C));
        System.out.println("Does B equal to A?");
        System.out.println(A.equals(B));
        System.out.println("Does A equal to A?");
        System.out.println(A.equals(A));

        System.out.println("====> Making C zero");
        C.makeZero();
        System.out.println("C has "+ C.getNNZ() +" non-zero entries:");
        System.out.println(C);

    }

}

