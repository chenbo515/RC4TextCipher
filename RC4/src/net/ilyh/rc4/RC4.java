package net.ilyh.rc4;

import java.io.UnsupportedEncodingException;


public class RC4
{
	public int[] box = new int[256];
	
	public RC4(String key) throws UnsupportedEncodingException
	{
		this(key.getBytes("UTF-8"));
	}

	public RC4(byte[] k){
        int i = 0, x = 0, t = 0, l = k.length;
 
        for(i=0; i<256; i++){
            box[i] = i;
        }
//        Log.d("message", Arrays.toString(k));
        for(i=0; i<256; i++){
            x = ((x+box[i]+k[i%l])+256) % 256;
 
            t = box[x];
            box[x] = box[i];
            box[i] = t;
        }
    }
 
    public byte[] make(byte[] data){
        int t, o, i=0, j = 0, l = data.length;
        byte[] out = new byte[l]; 
        for(int c=0; c<l; c++){
            i = (i+1) % 256;
            j = (j+box[i]) % 256;
 
            t = box[j];
            box[j] = box[i];
            box[i] = t;
 
            o = box[(box[i] + box[j]) % 256];
            out[c] = (byte)(data[c] ^ o);
        }
        return out;
    }
    
    public byte[] make(String data) throws UnsupportedEncodingException
    {
    	return this.make(data.getBytes("UTF-8"));
    }


}
