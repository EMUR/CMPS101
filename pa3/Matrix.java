/**
 * Created by eyad on 10/22/16.
 */
public class Matrix {

    private class Entry {
        double value;
        int column;

        Entry(double value, int column) {
            this.value = value;
            this.column = column;
        }

        public boolean equals(Object x) {
            boolean equals = false;
            Entry tempEntry;
            if (x instanceof Entry) {
                tempEntry = (Entry) x;
                equals = (this.column == tempEntry.column && this.value == tempEntry.value);
            }
            return equals;
        }

        public String toString() {
            return ("(" + String.valueOf(column) + ", " + String.valueOf(value) + ")");
        }
    }


    private List[] row;
    private int matrixSize;
    private int NNZ;

    // Constructor
    Matrix(int n) {
        matrixSize = n;
        row = new List[n];
        for (int i = 0; i < n; i++) {
            row[i] = new List();
        }
    }

    int getSize() {
        return (matrixSize);
    }

    int getNNZ() {
        return NNZ;
    }

    public boolean equals(Object x) {
        Matrix M;
        boolean areEquals = false;
        if (x instanceof Matrix) {
            M = (Matrix) x;
            if (M.getSize() == matrixSize && M.getNNZ() == NNZ) {

                for (int i = 0; i < matrixSize; i++) {
                    if (row[i].length() != M.row[i].length())
                        return false;

                    row[i].moveFront();
                    M.row[i].moveFront();
                    while (row[i].index() != -1) {
                        if (!(row[i].get().equals(M.row[i].get()))) {
                            return false;
                        }
                        row[i].moveNext();
                        M.row[i].moveNext();
                    }
                }
                areEquals = true;
            }
        }
        return areEquals;
    }

    // Manipulation procedures
    void makeZero() {
        if (matrixSize > 1)
            for (int i = 0; i < matrixSize; i++) {
                row[i].clear();
            }

        NNZ = 0;

    }// sets this Matrix to the zero state

    Matrix copy() {
        Matrix temp = new Matrix(matrixSize);
        for (int i = 0; i < matrixSize; i++) {
            row[i].moveFront();
            while (row[i].index() != -1) {
                Entry newEntry = new Entry(((Entry) row[i].get()).value, ((Entry) row[i].get()).column);
                temp.row[i].append(newEntry);
                temp.NNZ++;
                if (temp.NNZ == this.NNZ)
                    return temp;
                row[i].moveNext();
            }
        }
        return temp;
    }// returns a new Matrix having the same entries as this Matrix

    void changeEntry(int i, int j, double x) {
        if (i <= 0 || i > matrixSize || j > matrixSize || j <= 0 || matrixSize == 0) {
            throw new RuntimeException("Error in the changeEntry function!");
        }
        double spliter = j / matrixSize;
        if (spliter < 0.5) {
            for (row[i - 1].moveFront(); row[i - 1].index() != -1; row[i - 1].moveNext()) {
                if (((Entry) row[i - 1].get()).column == j) {
                    if (x == 0) {
                        NNZ--;
                        boolean deleted = false;
                        if (row[i - 1].front() == row[i - 1].get()) {
                            (row[i - 1]).deleteFront();
                            deleted = true;
                        }
                        if (row[i - 1].back() == row[i - 1].get()) {
                            (row[i - 1]).deleteBack();
                            deleted = true;
                        }
                        if (!deleted) {
                            (row[i - 1]).delete();
                        }
                        return;
                    }
                    ((Entry) row[i - 1].get()).value = x;
                    return;
                } else if (((Entry) row[i - 1].get()).column > j) {
                    if (x != 0) {
                        NNZ++;
                        Entry newEntry = new Entry(x, j);
                        row[i - 1].insertBefore(newEntry);
                    }
                    return;
                }
            }
            if (x != 0) {
                NNZ++;
                Entry newEntry = new Entry(x, j);
                row[i - 1].append(newEntry);
            }
            return;
        } else {
            for (row[i - 1].moveBack(); row[i - 1].index() != -1; row[i - 1].movePrev()) {
                if (((Entry) row[i - 1].get()).column == j) {
                    if (x == 0) {
                        NNZ--;
                        boolean deleted = false;
                        if (row[i - 1].front() == row[i - 1].get()) {
                            (row[i - 1]).deleteFront();
                            deleted = true;
                        }
                        if (row[i - 1].back() == row[i - 1].get()) {
                            (row[i - 1]).deleteBack();
                            deleted = true;
                        }
                        if (!deleted) {
                            (row[i - 1]).delete();
                        }
                        return;
                    }
                    ((Entry) row[i - 1].get()).value = x;
                    return;
                } else if (((Entry) row[i - 1].get()).column < j) {
                    if (x != 0) {
                        NNZ++;
                        Entry newEntry = new Entry(x, j);

                        if ((row[i - 1].get()) == (row[i - 1].back())) {
                            row[i - 1].append(newEntry);
                        } else {

                            row[i - 1].insertAfter(newEntry);
                        }
                    }
                    return;
                }
            }
            if (x != 0) {
                NNZ++;
                Entry newEntry = new Entry(x, j);
                row[i - 1].prepend(newEntry);
            }
            return;
        }
    }

    // changes ith row, jth column of this Matrix to x
    // pre: 1<=i<=getSize(), 1<=j<=getSize()
    Matrix scalarMult(double x) {

        Matrix temp = new Matrix(matrixSize);
        for (int i = 0; i < matrixSize; i++) {
            for (row[i].moveFront(); row[i].index() != -1; row[i].moveNext()) {
                Entry newEntry = (Entry) row[i].get();
                temp.changeEntry(i + 1, newEntry.column, newEntry.value * x);
                if (temp.NNZ == NNZ) return temp;
            }
        }
        return temp;
    }

    //    // returns a new Matrix that is the scalar product of this Matrix with x
    Matrix add(Matrix M) {
        if (getSize() != M.getSize())
            throw new RuntimeException("You can't add Matrices with different size!");
        if (M == this)
            return this.copy().scalarMult(2.0);
        Matrix A = new Matrix(getSize());
        for (int i = 0; i < getSize(); ++i) {
            List L = new List();
            row[i].moveFront();
            M.row[i].moveFront();
            while (row[i].index() >= 0 || M.row[i].index() >= 0) {
                if (row[i].index() >= 0 && M.row[i].index() >= 0) {
                    Entry a = (Entry) row[i].get();
                    Entry b = (Entry) M.row[i].get();
                    if (a.column > b.column) {
                        L.append(new Entry(b.value, b.column));
                        M.row[i].moveNext();
                    } else if (a.column < b.column) {
                        L.append(new Entry(a.value, a.column));
                        row[i].moveNext();
                    } else if (a.column == b.column) {
                        if ((a.value + b.value != 0))
                            L.append(new Entry(a.value + b.value, a.column));
                        row[i].moveNext();
                        M.row[i].moveNext();
                    }
                } else if (row[i].index() >= 0) {
                    Entry a = (Entry) row[i].get();
                    L.append(new Entry(a.value, a.column));
                    row[i].moveNext();
                } else {
                    Entry b = (Entry) M.row[i].get();
                    L.append(new Entry((b.value), b.column));
                    M.row[i].moveNext();
                }
            }
            A.row[i] = L;
        }
        return A;
    }


    //    // returns a new Matrix that is the sum of this Matrix with M
//    // pre: getSize()==M.getSize()
    Matrix sub(Matrix M) {
        if (getSize() != M.getSize())
            throw new RuntimeException("You can't subtract Matrices with different size!");
        if (M == this) {
            return new Matrix(getSize());
        }
        Matrix A = new Matrix(getSize());
        for (int i = 0; i < getSize(); ++i) {
            List L = new List();
            row[i].moveFront();
            M.row[i].moveFront();
            while (row[i].index() >= 0 || M.row[i].index() >= 0) {
                if (row[i].index() >= 0 && M.row[i].index() >= 0) {
                    Entry a = (Entry) row[i].get();
                    Entry b = (Entry) M.row[i].get();
                    if (a.column > b.column) {
                        L.append(new Entry(-b.value, b.column));
                        M.row[i].moveNext();
                    } else if (a.column < b.column) {
                        L.append(new Entry(a.value, a.column));
                        row[i].moveNext();
                    } else if (a.column == b.column) {
                        if ((a.value - b.value != 0))
                            L.append(new Entry((a.value - b.value), a.column));
                        row[i].moveNext();
                        M.row[i].moveNext();
                    }
                } else if (row[i].index() >= 0) {
                    Entry a = (Entry) row[i].get();
                    L.append(new Entry(a.value, a.column));
                    row[i].moveNext();
                } else {
                    Entry b = (Entry) M.row[i].get();
                    L.append(new Entry((-b.value), b.column));
                    M.row[i].moveNext();
                }
            }
            A.row[i] = L;
        }
        return A;
    }

    // returns a new Matrix that is the difference of this Matrix with M
    // pre: getSize()==M.getSize()
    Matrix transpose() {
        Matrix M = new Matrix(matrixSize);
        for (int i = 0; i < matrixSize; i++) {
            for (row[i].moveFront(); row[i].index() != -1; row[i].moveNext()) {
                Entry newCell = (Entry) row[i].get();
                int currCol = newCell.column;
                M.changeEntry(currCol, i + 1, newCell.value);
            }
        }
        return M;
    }

    // returns a new Matrix that is the transpose of this Matrix
    Matrix mult(Matrix M) {
        if (getSize() != M.getSize())
            throw new RuntimeException("You can multiply matrices with different sizes");
        Matrix A = new Matrix(getSize());
        Matrix B = M.transpose();
        for (int i = 0; i < getSize(); i++) {
            if (row[i].length() == 0) continue;
            for (int j = 0; j < getSize(); j++) {
                if (B.row[j].length() == 0) continue;
                A.changeEntry(i + 1, j + 1, dot(row[i], B.row[j]));
            }
        }
        return A;
    }

    // returns a new Matrix that is the product of this Matrix with M
    // pre: getSize()==M.getSize()

    private static double dot(List A, List B) {
        double productResult = 0.0;
        for (A.moveFront(); A.index() != -1; A.moveNext()) {
            Entry aEntry = (Entry) A.get();
            for (B.moveFront(); B.index() != -1; B.moveNext()) {
                Entry bEntry = (Entry) B.get();
                if (aEntry.column == bEntry.column) {
                    productResult += bEntry.value * aEntry.value;
                    break;
                }
            }
        }
        return productResult;
    }

    private int getEntry(int i, int j) {
        if ((i > matrixSize || i <= 0) || (j > matrixSize || j <= 0)) {
            return -1;
        }
        int entryIndex = -1;
        if (row[i - 1].length() != 0) {
            row[i - 1].moveFront();
            Entry compare;
            while (row[i - 1].index() != -1) {
                compare = (Entry) row[i - 1].get();
                if (compare.column == j) {
                    entryIndex = row[i - 1].index();
                    break;
                }
                row[i - 1].moveNext();
            }
        }
        return entryIndex;
    }

    // Other functions
    public String toString() {
        String s = "";
        for (int i = 0; i < matrixSize; i++) {
            if (row[i].length() == 0) {
                continue;
            }
            s = s + (i + 1) + ":" + row[i].toString() + "\n";
        }
        return s;

    }// overrides Object's toString() method

}
