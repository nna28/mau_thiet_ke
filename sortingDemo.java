
import java.util.Arrays;
import java.util.Scanner;

interface SortingStrategy {
    void sort(int[] array);
}

/*
 * cmt.
 */
class BubbleSort implements SortingStrategy {
    
    @Override
    public void sort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
}

/*
 * cmt.
 */
class SelectionSort implements SortingStrategy {
    @Override
    public void sort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
    }
}

/*
 * cmt.
 */
class SortingContext {
    private SortingStrategy strategy;

    public SortingContext(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    public void performSort(int[] array) {
        strategy.sort(array);
    }

    public void addSortingStrategy(SortingStrategy newStrategy) {
        // Implementation for adding new sorting strategy
    }

    public void performMultipleSorts(int[] array, SortingStrategy... strategies) {
        for (SortingStrategy strategy : strategies) {
            setStrategy(strategy);
            performSort(array);
            System.out.println(strategy.getClass().getSimpleName() + ": " + Arrays.toString(array));
        }
    }
}

/*
 * cmt.
 */
public class SortingDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập số lượng phần tử của mảng (n): ");
        
        while (!scanner.hasNextInt()) {
            System.out.println("Vui lòng nhập một số nguyên.");
            scanner.next();  // Đọc bỏ giá trị không phải số
        }
        
        int n = scanner.nextInt();
        int[] arrayToSort = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Nhập phần tử thứ " + (i + 1) + ": ");
            
            while (!scanner.hasNextInt()) {
                System.out.println("Vui lòng nhập một số nguyên.");
                scanner.next();  // Đọc bỏ giá trị không phải số
            }
            
            arrayToSort[i] = scanner.nextInt();
        }

        SortingContext context = new SortingContext(new BubbleSort());

        context.performSort(arrayToSort);
        System.out.println("Bubble Sort: " + Arrays.toString(arrayToSort));

        context.setStrategy(new SelectionSort());

        context.performSort(arrayToSort);
        System.out.println("Selection Sort: " + Arrays.toString(arrayToSort));
    }
}
