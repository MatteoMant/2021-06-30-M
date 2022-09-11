package it.polito.tdp.genes.model;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	GenesDao dao;
	private Graph<Integer, DefaultWeightedEdge> grafo;
	
	public Model() {
		dao = new GenesDao();
	}
	
	public void creaGrafo() {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
	
		// Aggiunta dei vertici
		Graphs.addAllVertices(this.grafo, this.dao.getAllChromosomes());
		
		// Aggiunta degli archi
		for (Adiacenza a : this.dao.getAllAdiacenze()) {
			Graphs.addEdge(this.grafo, a.getV1(), a.getV2(), a.getPeso());
		}
	}
	
	public double getPesoMinimo() {
		double minimo = 100;
		for (DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if (this.grafo.getEdgeWeight(e) < minimo) {
				minimo = this.grafo.getEdgeWeight(e);				
			}
		}
		return minimo;
	}
	
	public double getPesoMassimo() {
		double massimo = 0.0;
		for (DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if (this.grafo.getEdgeWeight(e) > massimo) {
				massimo = this.grafo.getEdgeWeight(e);				
			}
		}
		return massimo;
	}
	
	public int getNumeroArchiConPesoInferioreAllaSoglia(Double soglia) {
		int count = 0;
		for (DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if (this.grafo.getEdgeWeight(e) < soglia) {
				count++;
			}
		}
		return count;
	}
	
	public int getNumeroArchiConPesoMaggioreAllaSoglia(Double soglia) {
		int count = 0;
		for (DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if (this.grafo.getEdgeWeight(e) > soglia) {
				count++;
			}
		}
		return count;
	}
	
	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}
	
}