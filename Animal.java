class Animal {
  private String name;
  private String attack;
  private String health;
  private double aggression;

  Animal() {
    name = new String("animal");
    attack = new String("0");
    health = new String("0");
    aggression = 0.5;
  }

  Animal(String name_, String attack_, String health_, double agg) {
    name = name_;
    attack = attack_;
    health = health_;
    aggression = agg;
  }

  public double GetAggression() {
    return aggression;
  }

  public void SetAggression(String agg) {
    aggression = Double.parseDouble(agg);
  }

  public String GetName() {
    return name;
  }

  public Animal copy() {
    Animal a = new Animal();
    a.SetAttack(attack);
    a.SetHealth(health);
    a.SetName(name);
    return a;
  }

  public boolean isDead() throws NumberFormatException{
    try{
      int ihealth = Integer.parseInt(health);
      return ihealth <= 0;
    } catch(Exception e) {
      System.out.println("Error in Animal.isDead()");
      System.out.println(e);
    }
    return true;
  }

  public void SetName (String n) {
    name = n;
  }

  public void SetAttack (String n) {
    attack = n;
  }

  public void SetHealth (String n) {
    health = n;
  }

  public void Attack(Animal defender) throws NumberFormatException{
    try{
      // Turn the string into an integer for subtraction
      int me = Integer.parseInt(attack);
      int them = Integer.parseInt(defender.health);
      int difference = them - me;
      // A little bit of damage handling and client feedback
      if(difference <= 0) {
        // System.out.println("You killed a(n) " + defender.name);
        defender.SetHealth("0");
      } else {
        // System.out.println("You reduced a(n) " + defender.name + " to " + Integer.toString(difference));
        defender.SetHealth(Integer.toString(difference));
      }
    } catch(Exception e) {
      System.out.println("Error inside Animal.Attack()");
      System.out.println(e);
    }
  }// end of Attack

  // Outputs the current condition of the animal
  public void PrintAnimal() {

    System.out.println("name: " + name);
    System.out.println("health: " + health);
    System.out.println("attack: " + attack);
    System.out.println("aggression: " + aggression*100 + "%");
    
  }// end of PrintAnimal
}// end of Animal