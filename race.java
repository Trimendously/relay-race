// The user will first input the number of Runners checkpoints in the relay (each checkpoints will have a runner of both Runners)
// Next the user will input the fastest time for each racer (rounded to the closest whole second)
// The format for input will resemble something like this
// # of checkpoints (n)
// Runner 1 racer 1(time)   Runner 2  racer 1(time)
//       .                      .
//       .                      .
//       .                      .
//       .                      .
// Runner 1 racer n(time)   Runner 2  racer n(time)
// The approach to this problem will be using a greedy alogirthm with sweeping where needed. As I have wanted to try out coding this algorithm


import java.util.*;

public class Runner
{
  public ArrayList<Event> time = new ArrayList<Event>(); // Will stores all times in appropriate order
  public int n; // Stores the number of runner slots in the relay ie a 4x100 has 4 slots while a 3x300 has 3


  public static void main(String[] args)
  {
    Runner translation = new Runner();
    translation.read(); // Takes input
    translation.sort(); // Sorts times
    translation.sweep(); // Gets minimum time
  }

  public void read()
  {
    Scanner userInput = new Scanner(System.in); // Used to take user's input
    n = userInput.nextInt(); // The number of slots
    int a[] = new int[n]; // Team 1
    int b[] = new int[n]; // Team 2


    for (int i = 0; i < n; i++)
    {
      a[i] = userInput.nextInt();
      b[i] = userInput.nextInt();
      time.add(new Event(a[i],b[i]));
    }
  }

  public void sort()
  {
    int split = 0; // Used to split array list and sort again
    
    // First sorts by start times
    Collections.sort(time);
    for (Event y : time)
    {
      if (y.start> y.end) // If Team 1 takes longer than Team 2
        break;
      split++;
    }
    // Splits the array and sorts the latter half by end times
    Collections.sort(time.subList(split,n), Collections.reverseOrder());
  }

  public void sweep()
  {
    int sum = 0, x_sum = 0; // Used to keep track of total times
    for (int i = 0; i < time.size(); i++)
    {
      if (i == 0) // First iteration
      {
        sum = sum + time.get(0).start;
        x_sum = x_sum + time.get(0).start;
      }

      if ((i+1) < n) // Otherwise out of bounds
        x_sum = x_sum + time.get(i+1).start;

      sum = sum + time.get(i).end;

      if (x_sum > sum) // If Team 1 slows down Team 2
        sum = x_sum;
    }
    System.out.println(sum); // Prints solution
  }

  public static class Event implements Comparable<Event>
  {
     int start, end;

     Event(int start, int end)
     {
         this.start = start;
         this.end = end;
     }

     public int compareTo(Event o)
     {
        if ((this.start > this.end) && (o.start > o.end)) // If Team 1 slows Team 2 down
          return Integer.compare(this.end, o.end);

        if (o.start > o.end)
          return -1;

        if (this.start > this.end)
          return 1;

        return Integer.compare(this.start, o.start);
     }
 }
}
