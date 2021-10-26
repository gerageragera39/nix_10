package ua.com.alevel.level_1.task3;

import java.util.Scanner;

public class TriangleArea {

    public void calcTriangleArea() {
        Scanner scan = new Scanner(System.in);
        float A[] = new float[2];
        float B[] = new float[2];
        float C[] = new float[2];

        System.out.println("Enter coordinates A");
        System.out.print("A(x) = ");
        A[0] = scan.nextFloat();
        System.out.print("A(y) = ");
        A[1] = scan.nextFloat();

        System.out.println("Enter coordinates B");
        System.out.print("B(x) = ");
        B[0] = scan.nextFloat();
        System.out.print("B(y) = ");
        B[1] = scan.nextFloat();

        System.out.println("Enter coordinates C");
        System.out.print("C(x) = ");
        C[0] = scan.nextFloat();
        System.out.print("C(y) = ");
        C[1] = scan.nextFloat();

        boolean equationOfStraightLine = (C[0] - A[0]) / (B[0] - A[0]) == (C[1] - A[1]) / (B[1] - A[1]);

        if (!equationOfStraightLine) {
            float determinant = (float) (A[0] - C[0]) * (B[1] - C[1]) - (B[0] - C[0]) * (A[1] - C[1]);
            float area = (float) 1 / 2 * Math.abs(determinant);
            System.out.println();
            System.out.println("Area = " + area);
        } else {
            System.out.println();
            System.out.println("The points lie on one straight line, therefore the area of the triangle is 0");
        }
    }
}


//        System.out.println("Enter coordinates A");
//        String strACoordinates = scan.nextLine();
//        for (int i = 0; i < strACoordinates.length(); i++) {
//            if(Character.isDigit(strACoordinates.charAt(i))){
//                index++;
//            } else {
//                break;
//            }
//        }
//        for (int i = 0; i < index; i++) {
//            A[0] += Character.getNumericValue(strACoordinates.charAt(i));
//        }
//
//        int startOfSecondCoord = index + 1;
//        for (int i = startOfSecondCoord; i < strACoordinates.length(); i++) {
//            if(Character.isDigit(strACoordinates.charAt(i))){
//                index++;
//            } else {
//                break;
//            }
//        }
//        for (int i = startOfSecondCoord; i < index; i++) {
//            A[1] += Character.getNumericValue(strACoordinates.charAt(i));
//        }

