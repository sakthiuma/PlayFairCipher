/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package playfaircipher;

/**
 *
 * @author Sakthi
 */
import java.util.*;
public class PlayfairCipher {

    /**
     * @param args the command line arguments
     */
    public static char[][] keyMatrix=new char[5][5];
    public static int[] alphabet=new int[26];
    
    static int retRow(char a,char[][] keyMatrix)
    {
        int i,j;
        for(i=0;i<5;i++)
        {
            for(j=0;j<5;j++)
            {
                if(a==keyMatrix[i][j])
                    return i;
            }
        }
        return -1;
    }
    
    static int retCol(char a,char[][] keyMatrix)
    {
        int i,j;
        for(i=0;i<5;i++)
        {
            for(j=0;j<5;j++)
            {
                if(a==keyMatrix[i][j])
                    return j;
            }
        }
        return -1;
    }
    
    static String subEncrypt(char l,char r,char[][] keyMatrix)
    {
        StringBuilder ret_value=new StringBuilder();
        int row_l,col_l,row_r,col_r;
        row_l=retRow(l,keyMatrix);
        col_l=retCol(l,keyMatrix);
        row_r=retRow(r,keyMatrix);
        col_r=retCol(r,keyMatrix);
        
        if(row_l==row_r)
        {
            System.out.println("rows are same");
            ret_value.append(keyMatrix[row_l][(col_l+1)%5]);
            ret_value.append(keyMatrix[row_r][(col_r+1)%5]);
        }
        else if(col_l==col_r)
        {
            System.out.println("cols are same");
            ret_value.append(keyMatrix[(row_l+1)%5][col_l]);
            ret_value.append(keyMatrix[(row_r+1)%5][col_l]);
        }
        else
        {
            System.out.println("Both diff");
            ret_value.append(keyMatrix[row_r][col_l]);
            ret_value.append(keyMatrix[row_l][col_r]);
        }
        //System.out.print(ret_value.toString());
        return ret_value.toString();
    }
    
    static String subDecrypt(char l,char r,char[][] keyMatrix)
    {
        StringBuilder ret_value=new StringBuilder();
        int row_l,col_l,row_r,col_r;
        row_l=retRow(l,keyMatrix);
        col_l=retCol(l,keyMatrix);
        row_r=retRow(r,keyMatrix);
        col_r=retCol(r,keyMatrix);
        
        if(row_l==row_r)
        {
            System.out.println("rows are same");
            ret_value.append(keyMatrix[row_l][(col_l-1)%5]);
            ret_value.append(keyMatrix[row_r][(col_r-1)%5]);
        }
        else if(col_l==col_r)
        {
            System.out.println("cols are same");
            ret_value.append(keyMatrix[(row_l-1)%5][col_l]);
            ret_value.append(keyMatrix[(row_r-1)%5][col_l]);
        }
        else
        {
            System.out.println("Both diff");
            ret_value.append(keyMatrix[row_r][col_l]);
            ret_value.append(keyMatrix[row_l][col_r]);
        }
        //System.out.print(ret_value.toString());
        return ret_value.toString();
    }
    static char[][] retKeyMatrix(String key)
    {
        int i,j,k=0,index,l=0;
        for(i=0;i<26;i++)
            alphabet[i]=0;
        for(i=0;i<5;i++)
        {
            for(j=0;j<5;)
            {
                if(k<key.length())
                {
                    index=(int)(key.charAt(k)-'a');
                    if(alphabet[index]==0)
                    {
                        alphabet[index]=1;
                        keyMatrix[i][j]=key.charAt(k);
                        j++;
                    }
              
                    k++;
                }
                if(k==key.length() && l<26)
                {
                    if(alphabet[l]==0 && (l+97)!=106)
                    {
                        keyMatrix[i][j]=(char)(l+97);
                        j++;
                    }
                
                    l++;
                }
                
            }
        }
        for(i=0;i<5;i++)
        {
            for(j=0;j<5;j++)
            {
                System.out.print(keyMatrix[i][j]);
            }
            System.out.println();
        }
        return keyMatrix;
        
    }
    static String encrypt(String message,String key)
    {
        StringBuilder cipherText=new StringBuilder();
        String temp_value;
        char[][] key_matrix=new char[5][5];
        key_matrix=retKeyMatrix(key);
        int i,j,k;
        
        if(message.length()%2==1)
        {
            for(i=0;i<message.length();i+=2)
            {
                if(i<message.length()-1)
                {
                    if(message.charAt(i)!=message.charAt(i+1))
                    {
                        System.out.println("both char diff");
                        temp_value=subEncrypt(message.charAt(i),message.charAt(i+1),key_matrix);
                    }
                    else
                        temp_value=subEncrypt(message.charAt(i),'x',key_matrix);
                        
                    
                    //System.out.print(temp_value);
                }
                else
                {
                    temp_value=subEncrypt(message.charAt(i),'x',key_matrix);
                    //System.out.print(temp_value);
                }
                cipherText.append(temp_value);
            }
        }
        else
        {
            for(i=0;i<message.length();i+=2)
            {
                if(message.charAt(i)!=message.charAt(i+1))
                {
                    System.out.println("both char diff");
                    temp_value=subEncrypt(message.charAt(i),message.charAt(i+1),key_matrix);
                }
                else
                    temp_value=subEncrypt(message.charAt(i),'x',key_matrix);
                cipherText.append(temp_value);
            }
        }
        
        return cipherText.toString();
    }
    
    static String decrypt(String cipherText,String key)
    {
        StringBuilder plainText=new StringBuilder();
        String temp_value;
        char[][] key_matrix=new char[5][5];
        key_matrix=retKeyMatrix(key);
        int i,j,k;
        
        /*if(cipherText.length()%2==1)
        {
            for(i=0;i<cipherText.length();i+=2)
            {
                if(i<cipherText.length()-1)
                {
                    temp_value=subDecrypt(cipherText.charAt(i),cipherText.charAt(i+1),key_matrix);
                    
                    //System.out.print(temp_value);
                }
                else
                {
                    temp_value=subDecrypt(cipherText.charAt(i),'x',key_matrix);
                    //System.out.print(temp_value);
                }
                plainText.append(temp_value);
            }
        }*/
        
        
            for(i=0;i<cipherText.length();i+=2)
            {
                temp_value=subDecrypt(cipherText.charAt(i),cipherText.charAt(i+1),key_matrix);
                plainText.append(temp_value);
            }
        
        
        
        return plainText.toString();
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner s=new Scanner(System.in);
        String message,key,encrypted_message,decrypted_message;
        System.out.println("Enter the message");
        message=s.next();
        System.out.println("Enter the key");
        key=s.next();
        encrypted_message=encrypt(message,key);
        System.out.println("encrypted_message::"+encrypted_message);
        decrypted_message=decrypt(encrypted_message,key);
        System.out.println("decrypted_message::"+decrypted_message);
        
        //key-message
        //message-key
    }
}
