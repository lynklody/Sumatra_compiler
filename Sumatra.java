import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

/** Sumatra class includes scan() and parse()*/
public class Sumatra {
	
	private ArrayList<Token> tokenList;
	private int pos;
	private PrintStream out;
	private StringBuilder s;
	private int tab; // number of tabs
	private String filename;
	private ArrayList<Integer> linenum;
	private int linecount;
	boolean isComment;
	
	public Sumatra() throws FileNotFoundException
	{
		tokenList = new ArrayList<Token>();
		pos = 0;
		s = new StringBuilder();
		tab = 0;
		filename = "";
		linenum = new ArrayList<Integer>();
		linecount = 0;
		isComment = false;
	}
	
	public void scan() throws IOException
	{
		Scanner kbd = new Scanner(System.in);
		System.out.println("Enter the file name without extension:");
		String filename = kbd.nextLine();
		Scanner in = null;
		try
		{
			in = new Scanner(new FileInputStream(filename + ".sum"));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("can't find the file");
			System.exit(1);
		}
		while (in.hasNextLine())
		{
			String[] s = in.nextLine().trim().split(" ");
			++linecount;
			// use List Interface on array s to alter the array accordingly
			List<String> list = new ArrayList<String>(Arrays.asList(s));
			int offset = 0;
			int i = 0;
			loop:
			while (i < (s.length + offset))
			{
				String x = list.get(i);
				System.out.println("x= "+x);
				if (x.equals("{") && !isComment)
				{
					list.remove(x);
					--offset;
					System.out.println("removed "+ x);
					isComment = true;
					System.out.println("IN COMMENT");
				}
				else if (x.equals("}") && isComment)
				{
					int index = list.lastIndexOf(x);
					list.remove(index);
					--offset;
					System.out.println("removed "+x);
					isComment = false;
					System.out.println("OUT OF COMMENT");
					--i;
				}
				else if (x.contains("{") && !isComment)
				{
					int n = x.indexOf("{");
					list.set(i, x.substring(0, n));
					list.add(i+1, "{");
					list.add(i+2, x.substring(n+1));
					offset += 2;
				}
				else if (x.equals("{") && isComment)
				{
					System.out.println("Error! Unclosed comment before line "+linecount);
					System.exit(1);
				}
				else if (x.equals("}") && !isComment)
				{
					System.out.println("Error! Found \"}\" without \"{\" at line "+linecount);
					System.exit(1);
				}
				else if (x.contains("}"))
				{
					int n = x.indexOf("}");
					list.set(i, x.substring(0, n));
					list.add(i+1, "}");
					list.add(i+2, x.substring(n+1));
					offset += 2;
				}
				else if (isComment)
				{
					int index = list.lastIndexOf(x);
					list.remove(index);
					System.out.println("removed "+x);
					--offset;
				}
				else if (x.contains("[") && !x.equals("[") && !isComment)
				{
					int n = x.indexOf("[");
						list.set(i, x.substring(0, n));
						list.add(i+1, "[");
						list.add(i+2, x.substring(n+1));
						offset += 2;
				}
				else if (x.contains("]") && !x.equals("]") && !isComment)
				{
					int n = x.indexOf("]");
						list.set(i, x.substring(0, n));
						list.add(i+1, "]");
						list.add(i+2, x.substring(n+1));
						offset += 2;
				}
				else if (x.contains("(") && !x.equals("(") && !isComment)
				{
					int n = x.indexOf("(");
						list.set(i, x.substring(0, n));
						list.add(i+1, "(");
						list.add(i+2, x.substring(n+1));
						offset += 2;
				}
				else if (x.contains(")") && !x.equals(")") && !isComment)
				{
					int n = x.indexOf(")");
						list.set(i, x.substring(0, n));
						list.add(i+1, ")");
						list.add(i+2, x.substring(n+1));
						offset += 2;
				}
				else if (x.contains("-") && !x.equals("-") && !isComment)
				{
					int n = x.indexOf("-");
					list.set(i, x.substring(0, n));
					list.add(i+1, "-");
					list.add(i+2, x.substring(n+1));
					offset += 2;
				}
				else if (x.contains("+") && !x.equals("+") && !isComment)
				{
					int n = x.indexOf("+");
					list.set(i, x.substring(0, n));
					list.add(i+1, "+");
					list.add(i+2, x.substring(n+1));
					offset += 2;
				}
				else if (x.contains("*") && !x.equals("*") && !isComment)
				{
					int n = x.indexOf("*");
					list.set(i, x.substring(0, n));
					list.add(i+1, "*");
					list.add(i+2, x.substring(n+1));
					offset += 2;
				}
				else if (x.contains("/=") && !x.equals("/=") && !isComment)
				{
					int n = x.indexOf("/=");
					list.set(i, x.substring(0, n));
					list.add(i+1, "/=");
					list.add(i+2, x.substring(n+2));
					offset += 2;
				}
				else if (x.contains("/") && !x.equals("/") && !x.contains("/=") && !isComment)
				{
					int n = x.indexOf("/");
					list.set(i, x.substring(0, n));
					list.add(i+1, "/");
					list.add(i+2, x.substring(n+1));
					offset += 2;
				}
				else if (x.contains(":=") && !x.equals(":=") && !isComment)
				{
					int n = x.indexOf(":=");
					list.set(i, x.substring(0, n));
					list.add(i+1, ":=");
					list.add(i+2, x.substring(n+2));
					offset += 2;
				}
				else if (x.contains("=") && !x.equals("=") && !x.contains(":=") &&
						!x.contains("<=") && !x.contains(">=") && !x.contains("/=")
						 && !isComment)
				{
					int n = x.indexOf("=");
					if (x.charAt(n-1) != ':' && x.charAt(n-1) != '/')
					{
						list.set(i, x.substring(0, n));
						list.add(i+1, "=");
						list.add(i+2, x.substring(n+1));
						offset += 2;
					}
				}
				else if (x.contains(">=") && !x.equals(">=") && !isComment)
				{
					int n = x.indexOf(">=");
					list.set(i, x.substring(0, n));
					list.add(i+1, ">=");
					list.add(i+2, x.substring(n+2));
					offset += 2;
				}				
				else if (x.contains("<=") && !x.equals("<=") && !isComment)
				{
					System.out.println("x= "+x);
					int n = x.indexOf("<=");
					list.set(i, x.substring(0, n));
					list.add(i+1, "<=");
					list.add(i+2, x.substring(n+2));
					offset += 2;
				}
				else if (x.contains(">") && !x.equals(">") && !x.contains(">=") && !isComment)
				{
					int n = x.indexOf(">");
					list.set(i, x.substring(0, n));
					list.add(i+1, ">");
					list.add(i+2, x.substring(n+1));
					offset += 2;
				}
				else if (x.contains("<") && !x.equals("<") && !x.contains("<=") && !isComment)
				{
					int n = x.indexOf("<");
					list.set(i, x.substring(0, n));
					list.add(i+1, "<");
					list.add(i+2, x.substring(n+1));
					offset += 2;
				}
				else if (x.contains(",") && !x.equals(",") && !isComment)
				{
					int n = x.indexOf(",");
					list.set(i, x.substring(0, n));
					list.add(i+1, ",");
					list.add(i+2, x.substring(n+1));
					offset += 2;
				}
				else if (x.contains(";") && !x.equals(";") && !isComment)
				{
					int n = x.indexOf(";");
					list.set(i, x.substring(0, n));
					list.add(i+1, ";");
					list.add(i+2, x.substring(n+1));
					offset += 2;
				}
				if (!isComment &&
						((x.contains(",") && !x.equals(",")) || 
						(x.contains(";") && !x.equals(";")) || 
						(x.contains("[") && !x.equals("[")) || 
						(x.contains("]") && !x.equals("]")) ||
						(x.contains("(") && !x.equals("(")) ||
						(x.contains(")") && !x.equals(")")) ||
						(x.contains("+") && !x.equals("+")) ||
						(x.contains("-") && !x.equals("-")) ||
						(x.contains("*") && !x.equals("*")) ||
						(x.contains("/") && !x.equals("/") && !x.contains("/=")) ||
						(x.contains("/=") && !x.equals("/=")) ||
						(x.contains(":=") && !x.equals(":=")) ||
						(x.contains("<") && !x.equals("<") && !x.contains("<=")) ||
						(x.contains(">") && !x.equals(">") && !x.contains(">=")) ||
						(x.contains(">=") && !x.equals(">=")) ||
						(x.contains("<=") && !x.equals("<="))))
				{
					--i;
				}
				if (!isComment)
				{
					++i;
				}
			}
			//remove empty element in the ArrayList
			list.removeAll(Arrays.asList("", null));

			int count = 0;
			for (int j = 0; j < Integer.MAX_VALUE; ++j)
			{
				try
				{
					list.get(j);
					++count;
				}
				catch (IndexOutOfBoundsException e)
				{
					break;
				}
			}
			
			// add tokens in this line to ArrayList
			for (int j = 0; j < count; ++j)
			{
				String x = list.get(j);
				linenum.add(linecount);
				System.out.println(x);
				if (x.equals("program") || 
						x.equals(";") ||
						x.equals("end") ||
						x.equals("declare") ||
						x.equals("[") ||
						x.equals("]") ||
						x.equals(",") ||
						x.equals(":=") ||
						x.equals("read") ||
						x.equals("print") ||
						x.equals("if") ||
						x.equals("then") ||
						x.equals("else") ||
						x.equals("while") ||
						x.equals("do") ||
						x.equals("=") ||
						x.equals("/=") ||
						x.equals("<") ||
						x.equals(">") ||
						x.equals("<=") ||
						x.equals(">=") ||
						x.equals("-") ||
						x.equals("(") ||
						x.equals(")") ||
						x.equals("+") ||
						x.equals("-") ||
						x.equals("*") ||
						x.equals("/") ||
						x.equals("[") ||
						x.equals("]") ||
						x.equals("{") ||
						x.equals("}"))
				{
					tokenList.add(new Token(x,x));
				}
				else if (isIdentifier(x))
				{
					tokenList.add(new Token("identifier", x));
				}
				else if (isUnsigned_number(x))
				{
					tokenList.add(new Token("unsigned number", x));
				}
				else
				{
					System.out.println(x+" is an unidentified token.");
				}
			}
		}
		if (isComment)
		{
			System.out.println("Error! Unclosed comment until end of file");
			System.exit(1);
		}
	}
	
	public boolean isIdentifier(String x)
	{
		if (!Character.isLetter(x.charAt(0)))
		{
			return false;
		}
		for (int i = 1; i < x.length(); ++i)
		{
			if (!(Character.isDigit(x.charAt(i)) || 
					Character.isLetter(x.charAt(i))))
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean isUnsigned_number(String x)
	{
		for (int i = 0; i <x.length(); ++i)
		{
			if (!Character.isDigit(x.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean isLetter(String x)
	{
		if (Character.isLetter(x.charAt(0)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isDigit(String x)
	{
		if (Character.isDigit(x.charAt(0)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	
	
	
// parsing 
// pos numbers printed are the actual count (position number plus 1)
	public void parse() throws FileNotFoundException
	{
		parseProgram();
	}
	public void parseProgram() throws FileNotFoundException
	{
		parseProgram_header();
		parseDeclaration();
		parseStmts();
		parseProgram_footer();
	}
	public void parseProgram_header() throws FileNotFoundException
	{
		s.append("import java.io.*;\n");
		Token t = tokenList.get(pos++);
		if (!t.getLexeme().equals("program"))
		{
			System.out.println("program expected, found "+ t.getLexeme() + " instead at line #" + linenum.get(pos));
			System.exit(1);
		}
		t = tokenList.get(pos++);
		if (!t.getType().equals("identifier"))
		{
			System.out.println("identifier expected, found " + t.getLexeme() + " instead at line #" +  linenum.get(pos));
			System.exit(1);
		}
		filename = t.getLexeme();
		out = new PrintStream(filename + ".java");
		s.append("public class " + t.getLexeme() + "\n{\n");
		++tab;
		tab();
		s.append("public static void main(String[] args) throws IOException\n");
		tab();
		s.append("{\n");
		++tab;
		tab();
		s.append("BufferedReader in = new BufferedReader(new InputStreamReader(System.in));\n");
		t = tokenList.get(pos++);
		if (!t.getLexeme().equals(";"))
		{
			System.out.println("; expected, found "+ t.getLexeme() + " instead at line # " + linenum.get(pos));
			System.exit(1);
		}
	}
	public void parseProgram_footer()
	{
		Token t = tokenList.get(pos++);
		if (!t.getLexeme().equals("end"))
		{
			System.out.println("end expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		t = tokenList.get(pos++);
		if (!t.getLexeme().equals("program"))
		{
			System.out.println("program expected, found "+t.getLexeme()+" instead at line #"+linenum.get(pos));
			System.exit(1);
		}
		// the last token in the program (hopefully)
		t = tokenList.get(pos);
		if (!t.getLexeme().equals(";"))
		{
			System.out.println("; expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		else
		{
			System.out.println("Parsing complete!");
			tab--;
			tab();
			s.append("}\n");
			tab--;
			tab();
			s.append("}\n");
			out.print(s.toString());
			out.close();
		}
	}
	public void parseDeclaration()
	{
		Token t = tokenList.get(pos++);
		if (!t.getLexeme().equals("declare"))
		{
			System.out.println("declare expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		parseVarlist();
		t = tokenList.get(pos++);
		if (!t.getLexeme().equals(";"))
		{
			System.out.println("; expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
	}
	public void parseVarlist()
	{
		tab();
		s.append("int ");
		Token t = tokenList.get(pos++);
		if (!t.getType().equals("identifier"))
		{
			System.out.println("identifier expected, found " + t.getLexeme() + " instead at line # " + linenum.get(pos));
			System.exit(1);
		}
		// "identifier" is a copy of the identifier token
		Token identifier = new Token(t.getType(), t.getLexeme());
		t = tokenList.get(pos++);
		if (t.getLexeme().equals("["))
		{
			s.append("[] " + identifier.getLexeme() + " = new int [");
			t = tokenList.get(pos++);
			if (!t.getType().equals("unsigned number"))
			{
				System.out.println("unsigned number expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
				System.exit(1);
			}
			s.append(t.getLexeme());
			t = tokenList.get(pos++);
			if (!t.getLexeme().equals("]"))
			{
				System.out.println("] expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
				System.exit(1);
			}
			s.append("];\n");
		}
		else
		{
			pos--;
			s.append(identifier.getLexeme() + ";\n");
		}
		t = tokenList.get(pos++);
		if (t.getLexeme().equals(","))
		{
			parseVarlist();
		}
		else
		{
			pos--;
		}
	}
	public void parseStmts()
	{
		parseStmt();
		Token t = null;
		try
		{
			t = tokenList.get(pos++);
		}
		catch (IndexOutOfBoundsException e)
		{
			System.out.println("Error! Program without an ending statement");
			System.exit(1);
		}
		if (t.getLexeme().equals("{") || t.getLexeme().equals("}") ||
				t.getLexeme().equals("end") || t.getLexeme().equals("until") || 
				t.getLexeme().equals("else"))
		{
			pos--;
		}
		else
		{
			pos--;
			parseStmts();
		}
	}
	public void parseStmt()
	{
		Token t = tokenList.get(pos++);
		if (t.getLexeme().equals("read"))
		{
			System.out.println("read stmt");
			pos--;
			parseRead_stmt();
		}
		else if (t.getLexeme().equals("print"))
		{
			System.out.println("print stmt");
			pos--;
			parsePrint_stmt();
		}
		else if (t.getLexeme().equals("if"))
		{
			System.out.println("if stmt");
			pos--;
			parseIf_stmt();
		}
		else if (t.getLexeme().equals("while"))
		{
			System.out.println("while stmt");
			pos--;
			parseWhile_stmt();
		}
		else if (t.getLexeme().equals("do"))
		{
			System.out.println("do stmt");
			pos--;
			parseDo_stmt();
		}
		else if (isIdentifier(t.getLexeme()))
		{
			System.out.println("asg stmt");
			pos--;
			parseAsg_stmt();
		}
		else
		{
			System.out.println("stmt expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
	}
	public void parseAsg_stmt()
	{
		tab();
		parseVariable();
		Token t = tokenList.get(pos++);
		if (!t.getLexeme().equals(":="))
		{
			System.out.println(":= expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		s.append("= ");
		parseExpr();
		t = tokenList.get(pos++);
		if (!t.getLexeme().equals(";"))
		{
			System.out.println("; expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		s.append(";\n");
	}
	public void parseRead_stmt()
	{
		tab();
		Token t = tokenList.get(pos++);
		if (!t.getLexeme().equals("read"))
		{
			System.out.println("read expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		parseVariable();
		t = tokenList.get(pos++);
		if (!t.getLexeme().equals(";"))
		{
			System.out.println("; expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		s.append(" = Integer.parseInt(in.readLine().trim());\n");
	}
	public void parsePrint_stmt()
	{
		tab();
		s.append("System.out.println(");
		Token t = tokenList.get(pos++);
		if (!t.getLexeme().equals("print"))
		{
			System.out.println("print expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		parseExpr();
		s.append(")");
		t = tokenList.get(pos++);
		if (!t.getLexeme().equals(";"))
		{
			System.out.println("; expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		s.append(";\n");
	}
	public void parseIf_stmt()
	{
		tab();
		Token t = tokenList.get(pos++);
		if (!t.getLexeme().equals("if"))
		{
			System.out.println("if expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		s.append("if (");
		parseCond();
		s.append(")\n");
		tab();
		s.append("{\n");
		++tab;
		t = tokenList.get(pos++);
		if (!t.getLexeme().equals("then"))
		{
			System.out.println("then expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		parseStmts();
		--tab;
		tab();
		s.append("}\n");
		t = tokenList.get(pos++);
		if (t.getLexeme().equals("else"))
		{
			s.append("else\n{\n");
			++tab;
			tab();
			parseStmts();
			--tab;
			tab();
			s.append("}\n");
		}
		else
		{
			pos--;
		}
		t = tokenList.get(pos++);
		if (!t.getLexeme().equals("end"))
		{
			System.out.println("end expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		t = tokenList.get(pos++);
		if (!t.getLexeme().equals("if"))
		{
			System.out.println("if expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		t = tokenList.get(pos++);
		if (!t.getLexeme().equals(";"))
		{
			System.out.println("; expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
	}
	public void parseWhile_stmt()
	{
		tab();
		Token t = tokenList.get(pos++);
		if (!t.getLexeme().equals("while"))
		{
			System.out.println("while expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		s.append("while (");
		parseCond();
		s.append(")\n");
		tab();
		s.append("{\n");
		++tab;
		t = tokenList.get(pos++);
		if (!t.getLexeme().equals("do"))
		{
			System.out.println("do expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		parseStmts();
		t = tokenList.get(pos++);
		if (!t.getLexeme().equals("end"))
		{
			System.out.println("end expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		t = tokenList.get(pos++);
		if (!t.getLexeme().equals("while"))
		{
			System.out.println("while expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		t = tokenList.get(pos++);
		if (!t.getLexeme().equals(";"))
		{
			System.out.println("; expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		--tab;
		tab();
		s.append("}\n");
	}
	public void parseDo_stmt()
	{
		tab();
		Token t = tokenList.get(pos++);
		if (!t.getLexeme().equals("do"))
		{
			System.out.println("do expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		s.append("do\n");
		tab();
		s.append("{\n");
		++tab;
		parseStmts();
		--tab;
		tab();
		s.append("}\n");
		t = tokenList.get(pos++);
		if (!t.getLexeme().equals("until"))
		{
			System.out.println("until expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		tab();
		s.append("while (!(");
		parseCond();
		s.append("));\n");
		t = tokenList.get(pos++);
		if (!t.getLexeme().equals(";"))
		{
			System.out.println("; expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
	}
	public void parseCond()
	{
		parseExpr();
		parseRelop();
		parseExpr();
	}
	public void parseRelop()
	{
		Token t = tokenList.get(pos++);
		if (!(t.getLexeme().equals("=") || t.getLexeme().equals("/=") ||
				t.getLexeme().equals("<") || t.getLexeme().equals(">") ||
				t.getLexeme().equals("<=") || t.getLexeme().equals(">=")))
		{
			System.out.println("=, /=, <, >, <=, >= expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		if (t.getLexeme().equals("/="))
		{
			s.append(" != ");
		}
		else if(t.getLexeme().equals("="))
		{
			s.append(" == ");
		}
		else
		{
			s.append(" " + t.getLexeme() + " ");
		}
	}
	public void parseExpr()
	{
		Token t = tokenList.get(pos++);
		// negative number
		if (t.getLexeme().equals("-"))
		{
			s.append("-");
			t = tokenList.get(pos++);
			if (!isUnsigned_number(t.getLexeme()))
			{
				System.out.println("unsigned number expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
				System.exit(1);
			}
			s.append(t.getLexeme());
		}
		// variable name
		else if (isIdentifier(t.getLexeme()))
		{
			pos--;
			parseVariable();
		}
		// more expressions
		else if (t.getLexeme().equals("("))
		{
			s.append("(");
			parseExpr();
			parseArithop();
			parseExpr();
			t = tokenList.get(pos++);
			if (!t.getLexeme().equals(")"))
			{
				System.out.println(") expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
				System.exit(1);
			}
			s.append(")");
		}
		// positive number
		else
		{
			if (!isUnsigned_number(t.getLexeme()))
			{
				System.out.println("unsigned number expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
				System.exit(1);
			}
			s.append(t.getLexeme());
		}
	}
	public void parseArithop()
	{
		Token t = tokenList.get(pos++);
		if (!(t.getLexeme().equals("+") || t.getLexeme().equals("-") ||
				t.getLexeme().equals("*") || t.getLexeme().equals("/")))
		{
			System.out.println("+,-,*,/ expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		s.append(" " + t.getLexeme() + " ");
	}
	public void parseVariable()
	{
		Token t = tokenList.get(pos++);
		if (!t.getType().equals("identifier"))
		{
			System.out.println("; expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
			System.exit(1);
		}
		s.append(t.getLexeme());
		t = tokenList.get(pos++);
		if (t.getLexeme().equals("["))
		{
			s.append("[");
			parseExpr();
			t = tokenList.get(pos++);
			if (!t.getLexeme().equals("]"))
			{
				System.out.println("] expected, found "+t.getLexeme()+" instead at line # "+linenum.get(pos));
				System.exit(1);
			}
			s.append("]");
		}
		else
		{
			pos--;
		}
	}
	
	// print tab for output file formatting
	public void tab()
	{
		for (int i = 0; i < tab; ++i)
		{
			s.append("    ");
		}
	}
}
