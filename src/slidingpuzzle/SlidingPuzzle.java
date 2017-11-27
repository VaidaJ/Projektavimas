package slidingpuzzle;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class SlidingPuzzle {
    
    public static void main(String[] args) {
        int[][] puzzle = createPuzzle(3);
        int choise;
        Scanner sc = new Scanner(System.in);
        while(true){
            try{
            System.out.println("\n" + "SLIDING PUZZLE" + "\n");
            System.out.println("1. Pradeti zaidima");
            System.out.println("2. Keisti puzles didi");
            System.out.println("3. Baigti zaidima");
            System.out.println("Iveskite pasirinkima: ");              
            choise = sc.nextInt();
            switch(choise){
                case 1:
                    solvePuzzle(puzzle);   
                    break;
                case 2:
                    boolean n = true;
                    while(n == true){
                        try{
                            System.out.println("Pasirinkyte puzles dydi");
                            System.out.println("1. 3x3");
                            System.out.println("2. 4x4");
                            System.out.println("3. 5x5");
                            int dydzioNr  = sc.nextInt();
                            switch(dydzioNr){
                                case 1:
                                    puzzle = createPuzzle(3);
                                    n = false;
                                    break;
                                case 2:
                                    puzzle = createPuzzle(4);                                    
                                    n = false;
                                    break;
                                case 3:
                                    puzzle = createPuzzle(5);                                    
                                    n = false;
                                    break;                                   
                                default:
                                    System.out.println("Negalimas pasirinkimas! Iveskyte pasirinkima is naujo:");
                                    break;
                            }
                        }
                        catch (InputMismatchException e) {
                            System.out.print("Negalimas pasirinkimas! Iveskyte pasirinkima is naujo:" + "\n");
                            sc.next();
                        }
                    }            
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Negalimas pasirinkimas! Iveskyte pasirinkima is naujo:");
                    break;
            }
            }
            catch (InputMismatchException e) {
                System.out.print("Negalimas pasirinkimas! Iveskyte pasirinkima is naujo:" + "\n");
                sc.next();
            }
        }
    }  
    
    public static int[][] createPuzzle(int size){
        int value = 1;
        int[][] array = new int[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                array[i][j] = value;
                value++;
                if(i == size - 1 && j == size - 1)
                    array[i][j] = 0;
            }
        }
        return array;
    }
    
    public static void shufflePuzzle(int[][] array) {
        int number = 0, iNumber = 0, jNumber = 0, iReplace = 0, jReplace = 0, temp = 0, k = 100, size;
        boolean change = true;
        size = array.length * array.length -1;
        Random random = new Random();
        while(k > 0){
            while(change){
                number = random.nextInt(size) + 1;
                for(int i = 0; i < array.length; i++){
                    for(int j = 0; j < array[i].length; j++){
                        if(array[i][j] == number){
                            iNumber = i;
                            jNumber = j;
                        }
                        if(array[i][j] == 0){                
                            iReplace = i;
                            jReplace = j;
                        }
                    }
                }
                if((iNumber == iReplace || jNumber == jReplace) && (Math.abs(iNumber - iReplace) == 1 || Math.abs(jNumber - jReplace) == 1)){
                    temp = array[iNumber][jNumber];
                    array[iNumber][jNumber] = array[iReplace][jReplace];
                    array[iReplace][jReplace] = temp;
                    change = false;
                }
                else 
                    change = true;
            }
            change = true;
            k--;
        } 
    } 

    public static void printPuzzle(int[][] array){
        for(int i = 0; i < array.length + 1; i++){
            if(i == 0){
                System.out.print("  ");
            }
            else{ 
                System.out.print(" - ");
            }
        }
        System.out.println();
        for(int i = 0; i < array.length; i++){
            System.out.print(" | ");
            for(int j = 0; j < array[i].length; j++){

                if(array[i][j] == 0){
                    System.out.print("   ");
                }
                else if(array[i][j] < 10)
                    System.out.print(array[i][j] + "  ");
                else
                    System.out.print(array[i][j] + " ");
            }  
            System.out.println("|");
        }
        for(int i = 0; i < array.length + 1; i++){
            if(i == 0){
                System.out.print("  ");
            }
            else{ 
                System.out.print(" - ");
            }
        }
        System.out.println();
    }    

    public static void movePuzzle(int[][] array, int number){        
        int iNumber = 0, jNumber = 0, iReplace = 0, jReplace = 0, temp = 0;
        if(number > 0 && number < array.length*array.length){
            for(int i = 0; i < array.length; i++){
                for(int j = 0; j < array[i].length; j++){
                    if(array[i][j] == number){
                        iNumber = i;
                        jNumber = j;
                    }
                    if(array[i][j] == 0){
                        iReplace = i;
                         jReplace = j;
                    }
                }
            }
            if((iNumber == iReplace || jNumber == jReplace) && (Math.abs(iNumber - iReplace) == 1 || Math.abs(jNumber - jReplace) == 1)){
                temp = array[iNumber][jNumber];
                array[iNumber][jNumber] = array[iReplace][jReplace];
                array[iReplace][jReplace] = temp;        
            }
            else System.out.println("Negalimas perstumimas!");
        }
        else
            System.out.println("Negalimas pasirinkimas!"); 
    }        
    
    public static void solvePuzzle(int[][] array){
        long start, finish, spentMinutes, spentSeconds;
        int nr = 0, endGame = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("\n" + "Puzle deliojama pasirenkat skaiciu, kuri norima perstumti i tuscia vieta" + "\n" + "Jei norite nutraukti zaidima ivekite 0");
        shufflePuzzle(array);
        printPuzzle(array);
        start = System.currentTimeMillis();
        while(!puzzleIsSolved(array)){
            try {
                nr = sc.nextInt();
                if(nr == endGame)
                    break;
                movePuzzle(array, nr);
                System.out.println();
                printPuzzle(array);
            } 
            catch (InputMismatchException e) {
                System.out.print("Negalimas pasirinkimas!" + "\n");
                printPuzzle(array);
                sc.next();
            }
        }
        if(nr != 0){
        finish = System.currentTimeMillis();
        spentMinutes = TimeUnit.MILLISECONDS.toMinutes(finish-start);
        spentSeconds = TimeUnit.MILLISECONDS.toSeconds(finish-start-TimeUnit.MINUTES.toMillis(spentMinutes));
        System.out.println("Puzle isspresta!" + "\n" + "Laikas: " + spentMinutes + " min " + spentSeconds + " s");
        }

    }
    
    public static boolean puzzleIsSolved(int[][] array){
        int k = 0, arraySize = array.length*array.length;
        int[][] arrayCompare = createPuzzle(array.length);
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[i].length; j++){
                if(arrayCompare[i][j] == array[i][j]){
                    k++; 
                }
            }
        }    
        if(k == arraySize)
            return true;
        else 
            return false;
    }
}


