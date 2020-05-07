import java.util.HashMap;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

class FileParser {

  FileParser() {
    System.out.println("FileParser initialized.");
  }

  public static ArrayList<Animal> GetAnimals(String filename) {
    ArrayList<Animal> animals = new ArrayList<Animal>();

    try {
      File file = new File(filename);
      // Reads from the file
      BufferedReader br = new BufferedReader(new FileReader(file));

      boolean isNewAnimal = false;
      String line;
      int index = 0;
      // read the file line by line and interpret the line
      while((line = br.readLine()) != null) {
        // Finding the beginning and end of a "JSON" object
        if(line.contains("{")) {
          isNewAnimal = true;
          animals.add(new Animal());
          System.out.println("New animal created!");
        }
        if(line.contains("}")) {
          isNewAnimal = false;
          animals.get(index).PrintAnimal();
          System.out.println("");
          index++; // use instead of animals.size()-1
          continue;
        }

        // making the new animal
        if(isNewAnimal) {
          String[] pair = line.split("[: \t]");
          if(pair.length < 5 && line != null) continue; // making sure there enough splits, doesn't work if format of txt file is off
          // Edit the most recent animal in the list one field at a time
          // and output any field that isn't inside the Animal class
          switch(pair[2]) {
            case "name":
              animals.get(index).SetName(pair[4]);
              break;
            case "attack":
              animals.get(index).SetAttack(pair[4]);
              break;
            case "health":
              animals.get(index).SetHealth(pair[4]);
              break;
            case "aggression":
              animals.get(index).SetAggression(pair[4]);
            default:
              continue;
          }// end of switch
        }//end of if
      }// end of while loop

      // close buffered BufferedReader
      br.close();

    } catch (Exception e) {
      System.out.println("An error occured in FileParser:");
      System.out.println(e);
    }
    // System.out.println("debug:\n");
    // //debug
    // for(Animal a:animals) {
    //   a.PrintAnimal();
    // }
    // System.out.println("");
    
    return animals;
  } // end of ReadFile
}// end of FileParser