/*Given an array of events where events[i] = [startDayi, endDayi]. Every event i starts at startDayi and ends at endDayi.

You can attend an event i at any day d where startTimei <= d <= endTimei. Notice that you can only attend one event at any time d.

Return the maximum number of events you can attend.

Example 1:

Input: events = [[1,2],[2,3],[3,4]]
Output: 3
Explanation: You can attend all the three events.
One way to attend them all is as shown.
Attend the first event on day 1.
Attend the second event on day 2.
Attend the third event on day 3.
Example 2:

Input: events= [[1,2],[2,3],[3,4],[1,2]]
Output: 4
Example 3:

Input: events = [[1,4],[4,4],[2,2],[3,4],[1,1]]
Output: 4
Example 4:

Input: events = [[1,100000]]
Output: 1
Example 5:

Input: events = [[1,1],[1,2],[1,3],[1,4],[1,5],[1,6],[1,7]]
Output: 7
 

Constraints:

1 <= events.length <= 105
events[i].length == 2
1 <= startDayi <= endDayi <= 105 */

class Solution {
    public int maxEvents(int[][] events) {
        TreeSet<Integer> set = new TreeSet();
        for(int i=1;i<=100000;i++)
            set.add(i);
			//Fill the set with all the numbers from 1 to 100000(Max time limit for the events)
        int count=0;
        Arrays.sort(events,(a,b)-> a[1]-b[1]==0 ? a[0]-b[0] : a[1]-b[1]);
		//Sort the array based on end time
        for(int i=0;i<events.length;i++){
		//As the array is sorted know e can follow a greedy approach and fill the start time if found
            if(set.contains(events[i][0])){
			//If we encounter the start time of the event in the list remove
			//it from the set and increment the count
                set.remove(events[i][0]);
                count++;
            }
            else{
			//If not find the just next higher element for the current event and 
			//check whether it lies in the range of start to end time
                Integer high = set.higher(events[i][0]);
                if(high!=null && high<=events[i][1]){
                    count++;//Increment the count
                    set.remove(high); // Remove it from the set
                }
            }
        }
        return count;
    }
}
