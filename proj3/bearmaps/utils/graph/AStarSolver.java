package bearmaps.utils.graph;

import bearmaps.utils.pq.MinHeapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private MinHeapPQ<Vertex> pq;
    private SolverOutcome outcome;
    private List<Vertex> solution;
    private double solutionWeight;
    private int numExplored;
    private double timeSpent;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout){
        Stopwatch sw = new Stopwatch();
        pq = new MinHeapPQ<Vertex>();
        solution = new ArrayList<Vertex>();
        distTo = new HashMap<Vertex, Double>();
        edgeTo = new HashMap<Vertex, Vertex>();

        pq.insert(start, input.estimatedDistanceToGoal(start, end));
        distTo.put(start, 0.0);

        //While PQ is not empty
        while(pq.size() > 0){
            //If peek is the goal
            if(pq.peek().equals(end)){
                Vertex ptr = end;
                Stack<Vertex> temp = new Stack<Vertex>();
                while(!ptr.equals(start)){
                    temp.push(ptr);
                    ptr = edgeTo.get(ptr);
                }
                temp.push(ptr);
                int pathSize = temp.size();
                for(int i = 0; i < pathSize; i++){
                    solution.add(temp.pop());
                }
                solutionWeight = distTo.get(end);
                outcome = SolverOutcome.SOLVED;
                timeSpent = sw.elapsedTime();
                return;
            }
            //If time exceeds timeout
            if(sw.elapsedTime() > timeout){
                outcome = SolverOutcome.TIMEOUT;
                timeSpent = sw.elapsedTime();
                return;
            }
            Vertex p = pq.poll();
            numExplored += 1;
            relax(input, p, end);
        }
        outcome = SolverOutcome.UNSOLVABLE;
        timeSpent = sw.elapsedTime();
    }

    private void relax(AStarGraph<Vertex> input, Vertex cur, Vertex end){
        List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(cur);
        for(WeightedEdge<Vertex> e : neighborEdges){
            Vertex p = e.from();
            Vertex q = e.to();
            double w = e.weight();
            if(distTo.get(q) == null){
                distTo.put(q, Double.MAX_VALUE);
            }
            if(distTo.get(p) + w < distTo.get(q)){
                distTo.put(q, distTo.get(p) + w);
                edgeTo.put(q, p);
                if(pq.contains(q)){
                    pq.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                }else{
                    pq.insert(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                }
            }
        }
    }


    @Override
    public SolverOutcome outcome(){
        return outcome;
    }

    @Override
    public List<Vertex> solution(){
         return solution;
    }

    @Override
    public double solutionWeight(){
        return solutionWeight;
    }

    @Override
    public int numStatesExplored(){
        return numExplored;
    }

    @Override
    public double explorationTime(){
        return timeSpent;
    }
}