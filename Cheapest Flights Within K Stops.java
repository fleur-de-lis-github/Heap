/*There are n cities connected by some number of flights. You are given an array flights where flights[i] = [fromi, toi, pricei] indicates that there is a flight from city fromi to city toi with cost pricei.

You are also given three integers src, dst, and k, return the cheapest price from src to dst with at most k stops. If there is no such route, return -1.

Example 1:


Input: n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 1
Output: 200
Explanation: The graph is shown.
The cheapest price from city 0 to city 2 with at most 1 stop costs 200, as marked red in the picture.
Example 2:


Input: n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 0
Output: 500
Explanation: The graph is shown.
The cheapest price from city 0 to city 2 with at most 0 stop costs 500, as marked blue in the picture.
 

Constraints:

1 <= n <= 100
0 <= flights.length <= (n * (n - 1) / 2)
flights[i].length == 3
0 <= fromi, toi < n
fromi != toi
1 <= pricei <= 104
There will not be any multiple flights between two cities.
0 <= src, dst, k < n
src != dst */

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    

        Map<Integer,Map<Integer,Integer>> map = new HashMap<>();
        for(int[] f : flights){
            map.computeIfAbsent(f[0],m->new HashMap<>()).put(f[1],f[2]);
        }
        k++;
        int min = Integer.MAX_VALUE; 
        int[] count = new int[n];// 判断是否有环
        count[src]++; 
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{src,0});
        while(!queue.isEmpty()){
            int size = queue.size();
            if(k--==0) break;
            while(size-->0){
                int[] cur = queue.poll();
                Map<Integer,Integer> next = map.getOrDefault(cur[0],null);
                if(next==null) continue;
                for(Map.Entry<Integer,Integer> entry : next.entrySet()){
                    if(cur[1] + entry.getValue() >= min) continue;
                    if(entry.getKey()==dst){
                        min = Math.min(min,cur[1] + entry.getValue());
                        continue;
                    } 
                    if(count[entry.getKey()]++<=n){
                        queue.add(new int[]{entry.getKey(),cur[1] + entry.getValue()});    
                    }       
                }
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }
}
/* DFS
int min = Integer.MAX_VALUE;
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        Map<Integer,Map<Integer,Integer>> map = new HashMap<>();
        for(int f[] : flights){
            map.computeIfAbsent(f[0],m->new HashMap<>()).put(f[1],f[2]);
        }
        boolean[] visited = new boolean[n];
        visited[src] = true;
        dfs(map,visited,0,src,dst,K+1);
        return min == Integer.MAX_VALUE ? -1 : min;
    }
    private void dfs(Map<Integer,Map<Integer,Integer>> map,boolean[] visited,int val,int cur,int dst,int k){
        if(k<0) return;
        if(cur==dst){
            min = Math.min(min,val);
        }
        Map<Integer,Integer> next = map.getOrDefault(cur,null);
        if(next==null) return;
        for(Map.Entry<Integer,Integer> entry : next.entrySet()){
            if(visited[entry.getKey()]) continue;
            if(val+entry.getValue()>=min) continue;
            visited[entry.getKey()] = true;
            dfs(map,visited,val+entry.getValue(),entry.getKey(),dst,k-1);
            visited[entry.getKey()] = false;
        }
    }
PriorityQueue
public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer,Map<Integer,Integer>> map = new HashMap<>();
        for(int[] f : flights){
            map.computeIfAbsent(f[0],m->new HashMap<>()).put(f[1],f[2]);
        }
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((x,y)->x[0]-y[0]);
        minHeap.add(new int[]{0,src,k+1});
        while(!minHeap.isEmpty()){
            int[] cur = minHeap.poll();
            int price = cur[0], city = cur[1], stop = cur[2];
            if(stop < 0) continue;
            if(city==dst) return price;
            Map<Integer,Integer> next = map.getOrDefault(city,new HashMap<>());
            for(Map.Entry<Integer,Integer> entry : next.entrySet()){
                minHeap.add(new int[]{price+entry.getValue(),entry.getKey(),stop-1});
            }
        }
        return -1;
    }
Dynamic Programming
public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[][] dp = new int[k+2][n];
        for(int i=0; i<=k+1; i++){
            Arrays.fill(dp[i],Integer.MAX_VALUE);    
        }
        //fly from src to scr cost 0
        for(int i=0; i<=k+1; i++){
            dp[i][src] = 0;    
        }
        
        for(int i=1; i<=k+1; i++){
            for(int[] f: flights){
                if(dp[i-1][f[0]]!=Integer.MAX_VALUE){
                    dp[i][f[1]] = Math.min(dp[i][f[1]],dp[i-1][f[0]]+f[2]);
                }
            }
        }
        return dp[k+1][dst] == Integer.MAX_VALUE ? -1 : dp[k+1][dst];
    }    
    }
} */
