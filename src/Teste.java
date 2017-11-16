import org.gnu.glpk.GLPK;
import org.gnu.glpk.GLPKConstants;
import org.gnu.glpk.SWIGTYPE_p_double;
import org.gnu.glpk.SWIGTYPE_p_int;
import org.gnu.glpk.glp_prob;
import org.gnu.glpk.glp_smcp;
public class Teste {	
	
private static glp_prob lp = GLPK.glp_create_prob();
private static glp_smcp parm;
private static SWIGTYPE_p_int ind;
private static SWIGTYPE_p_double val;
public static int ret;
public String [] name;
public double [] valor;

	public Teste(double []var) {


	    try { 
	        // Create problem
	        lp = GLPK.glp_create_prob();
	        System.out.println("Problema criado!");
	        GLPK.glp_set_prob_name(lp, "Problema de Alocação");

	        // Definindo qtd colunas
	        GLPK.glp_add_cols(lp, var.length);
	        
	        int auxnome = 0;
	        
	        for(int i = 1; i<=var.length; i++) {
	        	if(i%3==1) {
	        		auxnome++;
	        	}
		        GLPK.glp_set_col_name(lp, i, "" +auxnome);
		        GLPK.glp_set_col_kind(lp, i, GLPKConstants.GLP_CV);
		        GLPK.glp_set_col_bnds(lp, i, GLPKConstants.GLP_DB, 0, 1);
	        }

	        // Create constraints

	        // Allocate memory
	        ind = GLPK.new_intArray(100);
	        val = GLPK.new_doubleArray(100);

	        // Create rows
	        GLPK.glp_add_rows(lp, (var.length/3)+3);

	        int aux = 0;
	        int a,b,c;
	        
	        for(int i = 1 ; i<=(var.length/3); i++) {
		        GLPK.glp_set_row_name(lp, i, "R" + i);
		        //igual a 1
		        GLPK.glp_set_row_bnds(lp, i, GLPKConstants.GLP_DB, 0, 1);
		        aux++;
		        GLPK.intArray_setitem(ind, 1, aux);
		        a = aux;
		        aux++;
		        GLPK.intArray_setitem(ind, 2, aux);
		        b = aux;
		        aux++;
		        GLPK.intArray_setitem(ind, 3, aux);
		        c = aux;
		        GLPK.doubleArray_setitem(val, a, 1);
		        GLPK.doubleArray_setitem(val, b, 1);
		        GLPK.doubleArray_setitem(val, c, 1);
		        GLPK.glp_set_mat_row(lp, i, 3, ind, val);
	        }
	        
	        // RESTRIÇÃO DE ATAQUE
	        GLPK.glp_set_row_name(lp, (var.length/3)+1, "RAT");
	        //igual a 1
	        GLPK.glp_set_row_bnds(lp, (var.length/3)+1, GLPKConstants.GLP_DB, 0, 1);
	        aux=1;
	        for(int i=1;i<=var.length;i+=3) {
	            GLPK.intArray_setitem(ind, aux, i);
	            GLPK.doubleArray_setitem(val, aux, 1);
	            aux++;
	        }
	        GLPK.glp_set_mat_row(lp, (var.length/3)+1, (var.length/3), ind, val);
	        
	        // RESTRIÇÃO DE DEFESA
	        GLPK.glp_set_row_name(lp, (var.length/3)+2, "RDF");
	        //igual a 1
	        GLPK.glp_set_row_bnds(lp, (var.length/3)+2, GLPKConstants.GLP_DB, 0, 1);
	        
	        aux=1;
	        
	        for(int i=2;i<=var.length;i+=3) {
	            GLPK.intArray_setitem(ind, aux, i);
	            GLPK.doubleArray_setitem(val, aux, 1);
	            aux++;
	        }
	        GLPK.glp_set_mat_row(lp, (var.length/3)+2, (var.length/3), ind, val);
	        

	        // RESTRIÇÃO DE SUPORTE
	        GLPK.glp_set_row_name(lp, (var.length/3)+3, "RSP");
	        //igual a 1
	        GLPK.glp_set_row_bnds(lp, (var.length/3)+3, GLPKConstants.GLP_DB, 0, 1);

	        aux=1;
	        
	        for(int i=3;i<=var.length;i+=3) {
	            GLPK.intArray_setitem(ind, aux, i);
	            GLPK.doubleArray_setitem(val, aux, 1);
	            aux++;
	        }
	        GLPK.glp_set_mat_row(lp, (var.length/3)+3, (var.length/3), ind, val);
	        
	        // Free memory
	        GLPK.delete_intArray(ind);
	        GLPK.delete_doubleArray(val);

	        
	        GLPK.glp_set_obj_name(lp, "z");
	        GLPK.glp_set_obj_dir(lp, GLPKConstants.GLP_MAX);
	        for(int i = 0; i<var.length; i++) {
	        	GLPK.glp_set_obj_coef(lp, i+1, var[i]);
	        }
	        
	        
	        // Write model to file
	         GLPK.glp_write_lp(lp, null, "lp.lp");

	        // Solve model
	        
	        parm = new glp_smcp();
	        GLPK.glp_init_smcp(parm);
	        ret = GLPK.glp_simplex(lp, parm);
	        
	        
	        // Retrieve solution
	        if (ret != 0) {
	            System.out.println("The problem could not be solved");
	        }
	        
	        int n;

	        valor = new double[var.length+1];
	        name = new String[var.length+1];
	        name[0] = GLPK.glp_get_obj_name(lp);
	        valor[0] = GLPK.glp_get_obj_val(lp);

	        n = GLPK.glp_get_num_cols(lp);
	        for (int i = 1; i <= n; i++) {
	            name[i] = GLPK.glp_get_col_name(lp, i);
	            valor[i] = GLPK.glp_get_col_prim(lp, i);
	        }

	        // Free memory
	        //GLPK.glp_delete_prob(lp);
	        
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}

	/*
	 * Método getVal() retorna um vetor de valores das respostas de cada variável
	 * sendo val[0] = valor de z
	 */
	public double [] getVal() {
	    int n = GLPK.glp_get_num_cols(this.lp);
		double [] val = new double[n];
	    val [0]= GLPK.glp_get_obj_val(this.lp);
	    for (int i = 1; i <= n; i++) {
	        val[i] = GLPK.glp_get_col_prim(this.lp, i);
	    }
		return val;
	}

	/*
	 * Método getNome() retorna um vetor de nomes de cada variável
	 * sendo val[0] = z
	 */
	public String [] getNome() {
	    int n = GLPK.glp_get_num_cols(this.lp);
		String [] nome = new String[n];
	    nome [0]= GLPK.glp_get_obj_name(this.lp);
	    for (int i = 1; i <= n; i++) {
	        nome [i]= GLPK.glp_get_col_name(this.lp, i);
	    }
		return nome;
	}
}