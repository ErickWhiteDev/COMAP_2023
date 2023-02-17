// WeightedGraph.java
// From Classic Computer Science Problems in Java Chapter 4
// Copyright 2020 David Kopec
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// Modified by Erick White

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.IntConsumer;

public class WeightedGraph<Vertex> extends Graph<Vertex, WeightedEdge> {

	public WeightedGraph(List<Vertex> vertices) {
		super(vertices);
	}

	// This is an undirected graph, so we always add
	// edges in both directions
	public void addEdge(WeightedEdge edge) {
		edges.get(edge.u).add(edge);
		edges.get(edge.v).add(edge.reversed());
	}

	public void addEdge(int u, int v, double weight) {
		addEdge(new WeightedEdge(u, v, weight));
	}

	public void addEdge(Vertex first, Vertex second, double weight) {
		addEdge(indexOf(first), indexOf(second), weight);
	}

	// Make it easy to pretty-print a Graph
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getVertexCount(); i++) {
			sb.append(vertexAt(i));
			sb.append(" -> ");
			sb.append(Arrays.toString(edgesOf(i).stream()
					.map(we -> "(" + vertexAt(we.v) + ", " + we.weight + ")").toArray()));
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

	public static double totalWeight(List<WeightedEdge> path) {
		return path.stream().mapToDouble(we -> we.weight).sum();
	}

	// Find the minimum-spanning tree of this graph using Jarnik's algorithm
	// *start* is the vertex index to start the search at
	public List<WeightedEdge> mst(int start) {
		LinkedList<WeightedEdge> result = new LinkedList<>(); // final mst
		if (start < 0 || start > (getVertexCount() - 1)) {
			return result;
		}
		PriorityQueue<WeightedEdge> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[getVertexCount()]; // where we've been

		// this is like a "visit" inner function
		IntConsumer visit = index -> {
			visited[index] = true; // mark as visited
			for (WeightedEdge edge : edgesOf(index)) {
				// add all edges coming from here to pq
				if (!visited[edge.v]) {
					pq.offer(edge);
				}
			}
		};

		visit.accept(start); // the first vertex is where everything begins
		while (!pq.isEmpty()) { // keep going while there are edges to process
			WeightedEdge edge = pq.poll();
			if (visited[edge.v]) {
				continue; // don't ever revisit
			}
			// this is the current smallest, so add it to solution
			result.add(edge);
			visit.accept(edge.v); // visit where this connects
		}

		return result;
	}

	public void printWeightedPath(List<WeightedEdge> wp) {
		for (WeightedEdge edge : wp) {
			System.out.println(vertexAt(edge.u) + " " + edge.weight + "> " + vertexAt(edge.v));
		}
		System.out.println("Total Weight: " + totalWeight(wp));
	}

	// Takes a map of edges to reach each node and return a list of
	// edges that goes from *start* to *end*
	public static List<WeightedEdge> pathMapToPath(int start, int end, Map<Integer, WeightedEdge> pathMap) {
		if (pathMap.size() == 0) {
			return List.of();
		}
		LinkedList<WeightedEdge> path = new LinkedList<>();
		WeightedEdge edge = pathMap.get(end);
		path.add(edge);
		while (edge.u != start) {
			edge = pathMap.get(edge.u);
			path.add(edge);
		}
		Collections.reverse(path);
		return path;
	}
}
