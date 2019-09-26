import java.io.*;
public class bubblesort
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int [] A = new int [10];
        int i;
        int j;
        int n;
        int tmp;
        i= 0;
        n = Integer.parseInt(in.readLine().trim());
        if (n != -1)
        {
            do
            {
                A[i]= n;
                n = Integer.parseInt(in.readLine().trim());
                i= (i + 1);
            }
            while (!(n == -1));
        }
        n= i;
        i= 0;
        while (i < n)
        {
            j= (i + 1);
            while (j < n)
            {
                if (A[i] > A[j])
                {
                    tmp= A[i];
                    A[i]= A[j];
                    A[j]= tmp;
                }
                j= (j + 1);
            }
            i= (i + 1);
        }
        i= 0;
        while (i < n)
        {
            System.out.println(A[i]);
            i= (i + 1);
        }
    }
}
