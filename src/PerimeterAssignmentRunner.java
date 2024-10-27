import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PerimeterAssignmentRunner {

    // Method to load shape points from a file
    public List<Point> loadShapeFromFile(File file) throws FileNotFoundException {
        List<Point> points = new ArrayList<>();
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String[] coordinates = scanner.nextLine().trim().split(",");
            int x = Integer.parseInt(coordinates[0].trim());
            int y = Integer.parseInt(coordinates[1].trim());
            points.add(new Point(x, y));
        }

        scanner.close();
        return points;
    }

    // Calculate perimeter of a shape
    public double getPerimeter(List<Point> shape) {
        double totalPerim = 0.0;
        Point prevPt = shape.get(shape.size() - 1); // Last point for closed shape

        for (Point currPt : shape) {
            double currDist = prevPt.distance(currPt);
            totalPerim += currDist;
            prevPt = currPt;
        }

        return Math.round(totalPerim * 100.0) / 100.0;
    }

    // Calculate the number of points in the shape
    public int getNumPoints(List<Point> shape) {
        return shape.size();
    }

    // Calculate the average length of the sides in the shape
    public double getAverageLength(List<Point> shape) {
        return Math.round((getPerimeter(shape) / getNumPoints(shape)) * 100.0) / 100.0;
    }

    // Find the longest side in the shape
    public double getLargestSide(List<Point> shape) {
        double largestSide = 0.0;
        Point prevPt = shape.get(shape.size() - 1); // Last point for closed shape

        for (Point currPt : shape) {
            double currDist = prevPt.distance(currPt);
            if (currDist > largestSide) {
                largestSide = currDist;
            }
            prevPt = currPt;
        }

        return Math.round(largestSide * 100.0) / 100.0;
    }

    // Calculate the largest perimeter among multiple shapes in files
    public double getLargestPerimeterMultipleFiles(File[] files) throws FileNotFoundException {
        double largestPerim = 0.0;

        for (File file : files) {
            List<Point> shape = loadShapeFromFile(file);
            double perimeter = getPerimeter(shape);
            if (perimeter > largestPerim) {
                largestPerim = perimeter;
            }
        }

        return largestPerim;
    }

    // Get the file name with the largest perimeter
    public String getFileWithLargestPerimeter(File[] files) throws FileNotFoundException {
        File largestFile = null;
        double largestPerim = 0.0;

        for (File file : files) {
            List<Point> shape = loadShapeFromFile(file);
            double perimeter = getPerimeter(shape);
            if (perimeter > largestPerim) {
                largestPerim = perimeter;
                largestFile = file;
            }
        }

        return largestFile != null ? largestFile.getName() : "No files found";
    }

    // Test method for perimeter calculations on a single file
    public void testPerimeter(File file) throws FileNotFoundException {
        List<Point> shape = loadShapeFromFile(file);
        System.out.println("Perimeter = " + getPerimeter(shape));
        System.out.println("Number of Points = " + getNumPoints(shape));
        System.out.println("Average Length = " + getAverageLength(shape));
        System.out.println("Largest Side = " + getLargestSide(shape));
    }

    // Test method for perimeter calculations on multiple files
    public void testPerimeterMultipleFiles(File[] files) throws FileNotFoundException {
        System.out.println("Largest Perimeter among files = " + getLargestPerimeterMultipleFiles(files));
    }

    // Test method to print the file with the largest perimeter
    public void testFileWithLargestPerimeter(File[] files) throws FileNotFoundException {
        System.out.println("File with Largest Perimeter = " + getFileWithLargestPerimeter(files));
    }

    public static void main(String[] args) {
        PerimeterAssignmentRunner pr = new PerimeterAssignmentRunner();

        // Replace with the path to your files for testing
        File file1 = new File("datatest1.txt");
        File[] files = {
                new File("dataset1.txt"),
                new File("dataset2.txt"),
                new File("dataset3.txt"),
                new File("dataset4.txt"),
                new File("dataset5.txt"),
                new File("dataset6.txt")
        };

        try {
            // Test on a single shape file
            pr.testPerimeter(file1);

            // Test on multiple shape files
            pr.testPerimeterMultipleFiles(files);

            // Find the file with the largest perimeter
            pr.testFileWithLargestPerimeter(files);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
