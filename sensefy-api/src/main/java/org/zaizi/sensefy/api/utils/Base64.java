/**
 * (C) Copyright 2015 Zaizi Limited (http://www.zaizi.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 3.0 which accompanies this distribution, and is available at 
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 **/
package org.zaizi.sensefy.api.utils;

import java.io.UnsupportedEncodingException;

/**
 * <p>
 * Base64 utility class
 * </p>
 * <p>
 * Provides methods to encoding and decoding using the base64 algorithm.
 * </p>
 * <p>
 * Based on the version written by Kevin Kelley (kelley@ruralnet.net).
 * </p>
 *
 * @author Antonio David Perez Morales <aperez@zaizi.com>
 * 
 */
public class Base64 {

    /* Public Fields */
    /**
     * <p>
     * No padding option for encoding.
     * </p>
     */
    public final static int NO_OPTIONS = 0;

    /**
     * <p>
     * No padding option for encoding.
     * </p>
     */
    public final static int NO_PADDING = 2;

    /**
     * Preferred Charset.
     */
    private final static String PREFERRED_CHARSET = "UTF-8";

    /**
     * <p>
     * The Base64 alphabet. Code characters for values 0..63.
     * </p>
     */
    private static String alphabetStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
    private static byte[] alphabet;

    static {
        try {
            alphabet = alphabetStr.getBytes(PREFERRED_CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    /**
     * <p>
     * Lookup table used to convert base64 characters to values in range 0..63.
     * </p>
     */
    private static byte[] codes = new byte[256];

    static {
        for (int i = 0; i < 256; i++) {
            codes[i] = -1;
        }

        for (int i = 'A'; i <= 'Z'; i++) {
            codes[i] = (byte) (i - 'A');

        }
        for (int i = 'a'; i <= 'z'; i++) {
            codes[i] = (byte) ((26 + i) - 'a');

        }
        for (int i = '0'; i <= '9'; i++) {
            codes[i] = (byte) ((52 + i) - '0');

        }
        codes['+'] = 62;
        codes['/'] = 63;
    }

    /**
     * <p>
     * Encodes an array of raw data returning a String of base64-encoded bytes with padding.
     * </p>
     * <p>
     *
     * @param data
     *            the array of bytes to encode
     * 
     * @return a {@code String} representing the base64-coded bytes with padding
     */
    public static String encode(byte[] data) {
        return encode(data, NO_OPTIONS);
    }
    
    /**
     * <p>
     * Encodes an array of raw data returning a String of base64-encoded bytes.
     * </p>
     * <p>
     * Valid Options:
     * </p>
     * <p>
     * <ul>
     * <li>NO_OPTIONS: Normal encoding with padding</li>
     * <li>NO_PADDING: Encoding without padding</li>
     * </ul>
     * </p>
     * 
     * @param data
     *            the array of bytes to encode
     * @param options
     *            an {@code Integer} representing the selected options
     * 
     * @return a {@code String} representing the base64-coded bytes
     */
    public static String encode(byte[] data, int options) {
        
        boolean padding = (options & NO_PADDING) == NO_PADDING ? false : true;
        
        byte[] out = new byte[((data.length + 2) / 3) * 4];
        //
        // 3 bytes encode to 4 chars. Output is always an even
        // multiple of 4 characters.
        //
        for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
            boolean quad = false;
            boolean trip = false;

            int val = (0xFF & (int) data[i]);
            val <<= 8;

            if ((i + 1) < data.length) {
                val |= (0xFF & (int) data[i + 1]);
                trip = true;
            }

            val <<= 8;

            if ((i + 2) < data.length) {
                val |= (0xFF & (int) data[i + 2]);
                quad = true;
            }

            out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
            val >>= 6;
            out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
            val >>= 6;
            out[index + 1] = alphabet[val & 0x3F];
            val >>= 6;
            out[index + 0] = alphabet[val & 0x3F];
        }

        String enc = new String(out);
        if(!padding)
            enc = enc.split("=")[0];
        
        return enc;
    }

    /**
     * <p>
     * Decodes a BASE-64 encoded stream to recover the original data.
     * </p>
     * <p>
     * White space before and after will be trimmed away, but no other
     * manipulation of the input will be performed.
     * </p>
     * 
     * @param encodedData
     *            the {@code String} representing the bytes to be decoded
     * 
     * @return an array of decoded base-64 bytes
     */
    public static byte[] decode(String encodedData) {
        
        switch (encodedData.length() % 4) // Pad with trailing '='s
        {
        case 0:
            break; // No pad chars in this case
        case 2:
            encodedData += "==";
            break; // Two pad chars
        case 3:
            encodedData += "=";
            break; // One pad char
        default:
            throw new Error("Illegal base64url string!");
        }
        
        byte[] data = encodedData.getBytes();

        // as our input could contain non-BASE64 data (newlines,
        // whitespace of any sort, whatever) we must first adjust
        // our count of USABLE data so that...
        // (a) we don't misallocate the output array, and
        // (b) think that we miscalculated our data length
        // just because of extraneous throw-away junk
        int tempLen = data.length;

        for (int ix = 0; ix < data.length; ix++) {
            if ((data[ix] > 255) || (codes[data[ix]] < 0)) {
                --tempLen; // ignore non-valid chars and padding
            }
        }

        // calculate required length:
        // -- 3 bytes for every 4 valid base64 chars
        // -- plus 2 bytes if there are 3 extra base64 chars,
        // or plus 1 byte if there are 2 extra.
        int len = (tempLen / 4) * 3;

        if ((tempLen % 4) == 3) {
            len += 2;

        }
        if ((tempLen % 4) == 2) {
            len += 1;

        }
        byte[] out = new byte[len];

        int shift = 0; // # of excess bits stored in accum
        int accum = 0; // excess bits
        int index = 0;

        // we now go through the entire array (NOT using the 'tempLen' value)
        for (int ix = 0; ix < data.length; ix++) {
            int value = (data[ix] > 255) ? (-1) : codes[data[ix]];

            if (value >= 0) { // skip over non-code
                accum <<= 6; // bits shift up by 6 each time thru
                shift += 6; // loop, with new bits being put in
                accum |= value; // at the bottom.

                if (shift >= 8) { // whenever there are 8 or more shifted in,
                    shift -= 8; // write them out (from the top, leaving any
                    out[index++] = (byte) ((accum >> shift) & 0xff);
                }
            }

            // we will also have skipped processing a padding null byte ('=')
            // here;
            // these are used ONLY for padding to an even length and do not
            // legally
            // occur as encoded data. for this reason we can ignore the fact
            // that
            // no index++ operation occurs in that special case: the out[] array
            // is
            // initialized to all-zero bytes to start with and that works to our
            // advantage in this combination.
        }

        // if there is STILL something wrong we just have to throw up now!
        if (index != out.length) {
            throw new Error("Miscalculated data length (wrote " + index
                    + " instead of " + out.length + ")");
        }

        return out;
    }

    /**
     * <p>
     * Encodes an array of raw data returning an array of base64-encoded url-safe bytes (without padding).
     * </p>
     * <p>
     * In order to make the encoded bytes be URL safe, this method replaces the
     * character "/" by "_" and the character "+" by "-".
     * </p>
     * 
     * @param data
     *            the array of bytes to encode
     * @return a {@code String} representing the url-safe base64-coded bytes
     */
    public static String encodeUrlSafe(byte[] data) {
        String s = encode(data, NO_PADDING); // Standard base64 encoder
        s = s.replace('+', '-'); // 62nd char of encoding
        s = s.replace('/', '_'); // 63rd char of encoding
        return s;
    }

    /**
     * <p>
     * Decodes a BASE-64 encoded stream to recover the original data.
     * </p>
     * <p>
     * White space before and after will be trimmed away, but no other
     * manipulation of the input will be performed.
     * </p>
     * 
     * <p>
     * In order to successfully decode the url-safe encoded data, this method
     * replaces the character "_" by "/" and the character "-" by "+"
     * </p>
     * 
     * @param data
     *            the {@code String} representing the bytes to be decoded
     * 
     * @return an array of decoded base-64 bytes
     */
    public static byte[] decodeUrlSafe(String data) {
        String s = data;
        s = s.replace('-', '+'); // 62nd char of encoding
        s = s.replace('_', '/'); // 63rd char of encoding
        switch (s.length() % 4) // Pad with trailing '='s
        {
        case 0:
            break; // No pad chars in this case
        case 2:
            s += "==";
            break; // Two pad chars
        case 3:
            s += "=";
            break; // One pad char
        default:
            throw new Error("Illegal base64url string!");
        }

        return decode(s); // Standard base64 decoder
    }
}

