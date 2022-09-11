package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Genes;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<Integer> getAllChromosomes(){
		String sql = "SELECT DISTINCT Chromosome "
				   + "FROM genes "
				   + "WHERE Chromosome != 0";
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getInt("Chromosome"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<Adiacenza> getAllAdiacenze(){
		String sql = "SELECT corrispondenza1.Chromosome AS v1, corrispondenza2.Chromosome AS v2, SUM(Expression_Corr) AS peso "
				+ "FROM (SELECT DISTINCT GeneID, Chromosome "
				+ "FROM genes) AS corrispondenza1, "
				+ "(SELECT DISTINCT GeneID, Chromosome "
				+ "FROM genes) AS corrispondenza2, "
				+ "interactions i "
				+ "WHERE i.GeneID1 = corrispondenza1.GeneId AND i.GeneID2 = corrispondenza2.GeneId AND corrispondenza1.Chromosome != 0 AND corrispondenza2.Chromosome != 0 "
				+ "AND corrispondenza1.Chromosome != corrispondenza2.Chromosome "
				+ "GROUP BY corrispondenza1.Chromosome, corrispondenza2.Chromosome";
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Adiacenza a = new Adiacenza(res.getInt("v1"), res.getInt("v2"), res.getDouble("peso"));
				result.add(a);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
}
