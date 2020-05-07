import java.util.ArrayList;

class Main {
  private static ArrayList<Animal> animals;
  public static void PrintAllAnimals(ArrayList<Animal> animals) {
    // print out every animal in animals
    System.out.println("--------------");
    for(Animal a : animals) {
      a.PrintAnimal();
      System.out.println("______________");
    }
    System.out.println("--------------");
  }
  public static void main(String[] args) {
    try{
      loadAnimals();
      // all animals participating
      // Main.PrintAllAnimals(animals);

      // get the winner of the whole tournament
      Animal supremeWinner = Arena.FightAll(animals, 10);
      // print who the winner is 
      System.out.println("--------------");
      System.out.println("The supreme winner is: ");
      supremeWinner.PrintAnimal();
      System.out.println("--------------");

    } catch(Exception e) {
      System.out.println(e);
    }
    System.out.println("Execution successful.");
  }// end of main

  // fill up the animals ArrayList from a file(already provided)
  public static void loadAnimals() {
    try {
      // load the animals from the text file into the ArrayList
      animals = FileParser.GetAnimals("animals.txt");

    } catch(Exception e) {
      System.out.println(e);
    }
  }// end of loadAnimals
}// end of Main