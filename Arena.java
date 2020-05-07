import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class AnimalWinPair{
  public int wins = 1;
  public Animal animal;

  // find the index within a list of an animal and return -1 if the animals doesn't exist
  public static int find(ArrayList<AnimalWinPair> animals, Animal targetAnimal) {
    // loop through all animals in the list
    for(int i = 0; i < animals.size(); i++) {
      // check to see if it exists by comparing the name values
      if(animals.get(i).animal.GetName().equals(targetAnimal.GetName())) return i; // return the position in the list where the item was found
    }
    // return an impossible index if the animal doesn't exist
    return -1;
  }

  //constructor of AnimalWinPair with an Animal as a parameter
  AnimalWinPair(Animal a) {
    animal = a;
  }

  //Increments the wins accumulated
  public void AddWin() {
    ++wins;
  }

  public static void SortList (ArrayList<AnimalWinPair> animals) {
    // sort the list
    Collections.sort(animals, new Comparator<AnimalWinPair>() {
      public int compare(AnimalWinPair a1, AnimalWinPair a2) {
        return a1.wins - a2.wins;// descending order
      }
    });

    // find how many elements to remove
    int count = animals.size() / 2 + animals.size() % 2;
    // remove the back half of the list
    if (count > 1) {
      for(int i = animals.size() - 1; i >= 0; i--) {
        animals.remove(i);
        // remove the element
      }
    }
  }
}

class Arena {
  public static Random rand = new Random();

  //pitch every Animal against each other in a round Robin
  // return the winner of the tournament based on a round robin, single elimination style
  public static Animal FightAll(ArrayList<Animal> animals, int iteration) {
    ArrayList<AnimalWinPair> winners = new ArrayList<AnimalWinPair>();
    ArrayList<Animal> animalWinners = new ArrayList<Animal>();
    try{
      // pitch each animal against a different animal one time
      for(int i = 0; i < animals.size(); i++) {
        // set all the arrays to be blank
        winners = new ArrayList<AnimalWinPair>();
        animalWinners = new ArrayList<Animal>();

        for(int j = i; j < animals.size(); j++) {
          if(j == i) continue; // make sure an animal doesn't accidentally fight itself
          boolean animal1Wins = Arena.Fight(animals.get(i), animals.get(j), iteration);
          if(animal1Wins) {// add animal1 to win list
            // get the index of an animal in the winners list
            int index = AnimalWinPair.find(winners, animals.get(i));
            if(index >= 0) {
              // increment their win if they are in the list
              winners.get(index).AddWin();
            } else {
              // add them new to the list
              winners.add(new AnimalWinPair(animals.get(i)));
              animalWinners.add(animals.get(i));
            }
          } else { // same as the above code but for animal2
            int index = AnimalWinPair.find(winners, animals.get(j));
            if(index >= 0) {
              winners.get(index).AddWin();
            } else {
              winners.add(new AnimalWinPair(animals.get(j)));
              animalWinners.add(animals.get(j));
            }
          }// end of adding to wins
        }// end of inner for
        // shrink the contestants by half
        AnimalWinPair.SortList(winners);
        // make the new animals list the winners and reset the winners to be blank
        Collections.copy(animals, animalWinners);
        // if there is only one winner left, return them
        if(winners.size() == 1) {
          return winners.get(0).animal;
        }
      } // end of outer for
    } catch(Exception e) {
      System.out.println("Error occured in Arena.FightAll()");
      System.out.println(e);
    }
    // the final winner of the system
    return winners.get(0).animal;
  }

  //true -> animal1 wins, false -> animal2 wins
  public static boolean Fight (Animal animal1, Animal animal2, int iteration) {
    int animal1WinCount = 0;
    for(int i = 0; i < iteration; i++) {
      Animal copy1 = animal1.copy();//copy of animal1
      Animal copy2 = animal2.copy();//copy of animal2
    
      while(!copy1.isDead() && !copy2.isDead()) {
        // determine who attacks when
        boolean animal1Attack = Arena.CoinFlip(animal1.GetAggression());
        boolean animal2Attack = Arena.CoinFlip(animal2.GetAggression());
        // We want the more aggressive animal to attack the other
        if(animal1.GetAggression() > animal2.GetAggression() && animal1Attack && !animal2Attack) {          
          copy1.Attack(copy2); // animal1 is more aggressive and they chose to attack when animal2 didn't
        } else if(animal1.GetAggression() < animal2.GetAggression() && !animal1Attack && animal2Attack) {
          copy2.Attack(copy1); // animal2 is more aggressive and they chose to attack when animal1 didn't
        } else if(animal1Attack && animal2Attack) {
          // both animals chose to attack each other
          copy1.Attack(copy2);
          copy2.Attack(copy1);
        }
      }
      // check to see if the animal1 has won
      if (copy2.isDead() && !copy1.isDead()){
        animal1WinCount++;
      } 
    }
    // see if animal1 has won more than half the iterations in the list
    return animal1WinCount > (Integer)(iteration/2 + iteration%2);
  }

  // Performs a coin flip with optional values of percent chance
  // use as 1 = H (win) and 0 = T (lose)
  public static boolean CoinFlip(double chance) {
    return rand.nextDouble() < chance;
  }
  public static boolean CoinFlip() {
    return rand.nextDouble() < 0.5;
  }
}// end of Arena class