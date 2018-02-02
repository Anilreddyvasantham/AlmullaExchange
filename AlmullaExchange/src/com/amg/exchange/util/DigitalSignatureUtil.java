package com.amg.exchange.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;

public class DigitalSignatureUtil {

	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	// Convert Base64 String image value to Hex Image String
	public static final String convertBase64toHex(String base64String) throws IOException {

		BASE64Decoder decoder = new BASE64Decoder();
		byte[] decodedBytes = decoder.decodeBuffer(base64String);

		final StringBuffer sb = new StringBuffer(decodedBytes.length * 2);
		for (int i = 0; i < decodedBytes.length; i++) {
			sb.append(DIGITS[(decodedBytes[i] >>> 4) & 0x0F]);
			sb.append(DIGITS[decodedBytes[i] & 0x0F]);
		}
		return sb.toString();
	}

	// Convert Hex String image value to Base64 Image String
	public static String convertHexToBase64(String hexString) {
		String base64String = null;
		if ((hexString.length()) % 2 > 0)
			throw new NumberFormatException("Input string was not in a correct format.");
		byte[] buffer = new byte[hexString.length() / 2];
		int i = 2;
		while (i < hexString.length()) {
			buffer[i / 2] = (byte) Integer.parseInt(hexString.substring(i, i + 2), 16);
			i += 2;
		}

		base64String = Base64.encodeBase64String(buffer);
		base64String = replaceCharAt(base64String, 0, 'i');
		base64String = replaceCharAt(base64String, 1, 'V');

		return base64String;

	}

	// Covert to Blob Image from Base64 String
	public static void convertBase64ToBlob(String base64String) {

		try {

			BASE64Decoder decoder = new BASE64Decoder();
			byte[] decodedBytes = decoder.decodeBuffer(base64String);

			String uploadFile = "c:\\tmp\\test.png";
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedBytes));

			if (image == null) {
				System.out.println("not available");
				// Log.error("Buffered Image is null");
			}
			File f = new File(uploadFile);

			// write the image
			ImageIO.write(image, "png", f);

		} catch (Exception e) {

		}
	}

	// Convert base64 to Blob binary stream
	public static byte[] Base64ToBytes(String imageString) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] decodedBytes = decoder.decodeBuffer(imageString);
		return decodedBytes;
	}

	// Replace character for particular position
	public static String replaceCharAt(String s, int pos, char c) {
		return s.substring(0, pos) + c + s.substring(pos + 1);
	}

	/*
	 * public static void main(String[] s) throws Exception {
	 * 
	 * String hexString =
	 * "FFFFFFFFAE2513437200000006000000182606FAB52FFB7878E6D55021820F8942C622FBAFDD68BE5ADDC4B95B56E4E05ADDC4B95B56E4E0C0B9258BC1EA9B5E9CCDD78FCA53F65D7F27FCE67B16EDDA23E8C590C08371F1170FF496A6C32CC237163701E83968500B9EAAB661DE0649EE3668CFE9432108FF7D7FA707CD3133E32BBC96B5832B6411B22FB1AF9638A10F074ACF20B9AC4FD7782B76C9838585D169A114513FCA3D73EFF549E6F27D081C36D46B19CC4B4243FE50BBAD42424E25BAE453884E3D646ACF74795D76F48B3BA1CDBBA139EA703525BD8A770200094E5FE4510D62B34C5E521D010AFD9E09AFF9D6FE759A5043DBC9046122885F6DBF352B061C25C6F64DBF4C68F45522EABD23359B2F512CB05BCC7CCBA3A854326A89C3DAFBFD6808090FAB55E84B4723D4519E4E8818A0D774B67701AE4AD131816832DD7680EA5A2FD9FD588A348B361C43DFB23813F8B7C558D7702E4248068FD875D5E665D4CFF2E1687794BAEE503C48CF814EA5F3460FF41C8E34D19A0DA167D402D6D14846E72E1D8CA8ECFA9B9D600DE56AD1652D4999390EA88F44ED66B5AE8D75F7571A135D2F770A2D48155C56426C68E870C6D4F0A0836A5DCAD35ADDC4B95B56E4E05C3F4F81EE4CA15373A3318ED4A680D78F7B485ED0F67AA54CDBCF124464B851D6181B7DFB549B0D01C2212E66BC19931BB567389C97AB7ED01FBF54030E474D22E022A64631828B93A95CE56ACE6EF63D573BE1990202C91D654934E642D67A152436BBB9EEE18729E4029ECF9166D8A4B985FB20AB2F1528D92C6108C479B5FF91A2BD855A230157291D34581126F470A1E7192A638697B04AE8A493FDB1C1F1224004B3DDF3B6211CA8A332262E829CC7DA5443182EB20D69ACB9181A11E4C02980A2BB4892236EDD4026663927B34CCC25E7E96566B04E5F625ACBCAF8D63564CF3DB1148AEA27D43A611FD90B1239104FB81812A0A61E0A3A88ECC43378B356C9355FD37B5ED50AB9429C3C8D0D5F499265F634CFF78F5B48EE22061DDC3216E4ED49C6EF60D97FBAEAA41C7CEAC77B7A7B5D8D9B196673FDFAB94F9B90B3E4325B4C2DB81B176E6CA471E45CB2BB3F2E5A81A9719E9968D057FEB8726A147391CA1B5B6C86CF957303BBD2138DAE52A4AD136FDB4EF3E778D1D7DBC334469DDFFEB2C07E1B738D1E67EC0E5A870FC370C9D16AECCEB92B46C2A6C9AF93B9E94434814B6456E424A6CED3238C995055FB6690A280DEF06703A0524B7BE02DC752FAF7006A0946B1209975B563532980BB139E8B0C8E"
	 * ; String s1 = convertHexToBase64(hexString);
	 * 
	 * System.out.println(s1);
	 * 
	 * 
	 * 
	 * }
	 */

}
