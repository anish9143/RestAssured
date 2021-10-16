package com.API.sampleTests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class BizSOAPAutomation {
	static String host = "http://10.132.61.242:9080";
	static String un = "a";
	static String pw = "a", publicId = "65380", privateId = "65382";

	public static void main(String[] args) {

		for (int i = 1; i < 10; i++) {
			host = "http://10.132.61.242:9080";
			un = "a";
			pw = "a";
			publicId = "65380";
			privateId = "65382";

			String value = "NAESBSender";

			// Receiver BIZ
			Magic(value + i, false, true);

			value = "NAESBReceiver";

			Magic(value + i, true, true);

			// Intiating to SenderBiz
			host = "http://10.132.61.240:9280";
			pw = "Password1!";
			publicId = "397006";
			privateId = "397008";
			value = "NAESBSender";

			Magic(value + i, false, false);

			value = "NAESBReceiver";

			Magic(value + i, false, true);
		}
	}

	private static void PGP(long partyId) {
		Map<String, String> pgpInfo = new HashMap<String, String>();
		pgpInfo.put("pgp_public_key_ring_id", publicId);
		pgpInfo.put("pgp_pass_phrase", "___TOKV2E08B27A256509E60306D1AB0A1F68B17");
		pgpInfo.put("pgp_key_id", "Key1");
		pgpInfo.put("pgp_private_key_ring_id", privateId);

		pgpInfo.forEach((i, j) -> {
			System.out.println("Updating: " + i);
			RestCall(host + "/axis/services/PartyConfigServer",
					"<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:WsPartyConfigServer\">\r\n"
							+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>\r\n"
							+ "      <urn:updatePartyProperty soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\r\n"
							+ "         <loginName xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
							+ un + "</loginName>\r\n"
							+ "         <password xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
							+ pw + "</password>\r\n" + "         <partyId xsi:type=\"xsd:long\">" + partyId
							+ "</partyId>\r\n"
							+ "         <name xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
							+ i + "</name>\r\n"
							+ "         <newValue xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
							+ j + "</newValue>\r\n" + "      </urn:updatePartyProperty>\r\n" + "   </soapenv:Body>\r\n"
							+ "</soapenv:Envelope>");
		});

		Map<String, String> addInfo = new HashMap<String, String>();
		addInfo.put("pgp_sym_comp_alg", "None");
		// addInfo.put("pgp_fips_mode", "0");
		addInfo.put("pgp_encryption_behavior", "no");
		addInfo.forEach((i, k) -> {
			System.out.println("Adding: " + i);
			RestCall(host + "/axis/services/PartyConfigServer",
					"<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:WsPartyConfigServer\">\r\n"
							+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>\r\n"
							+ "      <urn:addPartyProperty soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\r\n"
							+ "         <loginName xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
							+ un + "</loginName>\r\n"
							+ "         <password xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
							+ pw + "</password>\r\n" + "         <partyId xsi:type=\"xsd:long\">" + partyId
							+ "</partyId>\r\n"
							+ "         <name xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
							+ i + "</name>\r\n"
							+ "         <value xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
							+ k + "</value>\r\n" + "      </urn:addPartyProperty>\r\n" + "   </soapenv:Body>\r\n"
							+ "</soapenv:Envelope>");
		});
	}

	private static void Magic(String value, boolean filecopy, boolean transport) {

		long partyId = 0;
		/**
		 * Party
		 */
		String restCall = RestCall(host + "/axis/services/PartyConfigServer",
				"<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:WsPartyConfigServer\">\r\n"
						+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>\r\n"
						+ "      <urn:addParty soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\r\n"
						+ "         <loginName xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
						+ un + "</loginName>\r\n"
						+ "         <password xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
						+ pw + "</password>\r\n"
						+ "         <name xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
						+ value + "</name>\r\n" + "         <typeCode xsi:type=\"xsd:int\">1</typeCode>\r\n"
						+ "         <contactData xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">&lt;ContactData /></contactData>\r\n"
						+ "      </urn:addParty>\r\n" + "   </soapenv:Body>\r\n" + "</soapenv:Envelope>");

		if (restCall != null) {
			Document convertStringToXMLDocument = convertStringToXMLDocument(restCall);
			Node elementsByTagName = convertStringToXMLDocument.getElementsByTagName("id").item(0);
			partyId = Long.parseLong(elementsByTagName.getTextContent());
			/**
			 * Identifiers
			 */
			restCall = RestCall(host + "/axis/services/PartyConfigServer",
					"<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:WsPartyConfigServer\">\r\n"
							+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>\r\n"
							+ "      <urn:addPartyIdentity soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\r\n"
							+ "         <loginName xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
							+ un + "</loginName>\r\n"
							+ "         <password xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
							+ pw + "</password>\r\n" + "         <partyId xsi:type=\"xsd:long\">" + partyId
							+ "</partyId>\r\n"
							+ "         <identifier xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
							+ value + "</identifier>\r\n"
							+ "         <qualifier xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">01</qualifier>\r\n"
							+ "      </urn:addPartyIdentity>\r\n" + "   </soapenv:Body>\r\n" + "</soapenv:Envelope>");

			if (restCall != null) {
				PGP(partyId);
				if (transport) {
					String TX = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:WsAgentConfigServer\">\r\n"
							+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>\r\n"
							+ "      <urn:addTransport soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\r\n"
							+ "         <loginName xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
							+ un + "</loginName>\r\n"
							+ "         <password xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
							+ pw + "</password>\r\n" + "         <partyId xsi:type=\"xsd:long\">" + partyId
							+ "</partyId>\r\n"
							+ "         <name xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">NAESB</name>\r\n"
							+ "         <agentTypeCode xsi:type=\"xsd:long\">16</agentTypeCode>\r\n"
							+ "         <scheduleData xsi:type=\"xsd:string\"><![CDATA[<Schedule><outer><daily><every>1</every></daily></outer><inner/><exclude /><active>on</active></Schedule>]]></scheduleData>\r\n"
							+ "         <data xsi:type=\"xsd:string\"><![CDATA[<NAESBOutbound><To>" + value
							+ "</To><Pgp><EncryptedFormat>MIME</EncryptedFormat></Pgp><ForwardAprf></ForwardAprf><RequestSignedReceipt>false</RequestSignedReceipt><Http><Support100Continue>false</Support100Continue><genericTLS><GenericTLS><Xtended><TLS><VerifyPeerCertificates>false</VerifyPeerCertificates><TLSClientAuthPolicy>TLS_CAPOLICY_NONE</TLSClientAuthPolicy><SupportedCipherSuites><Cipher>TLS_AES_256_GCM_SHA384</Cipher><Cipher>TLS_AES_128_GCM_SHA256</Cipher><Cipher>TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384</Cipher><Cipher>TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384</Cipher><Cipher>TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256</Cipher><Cipher>TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256</Cipher><Cipher>TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384</Cipher><Cipher>TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256</Cipher><Cipher>TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384</Cipher><Cipher>TLS_DHE_RSA_WITH_AES_256_GCM_SHA384</Cipher><Cipher>TLS_DHE_RSA_WITH_AES_128_GCM_SHA256</Cipher><Cipher>TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384</Cipher><Cipher>TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA</Cipher><Cipher>TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256</Cipher><Cipher>TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA</Cipher><Cipher>TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA</Cipher><Cipher>TLS_DHE_RSA_WITH_AES_256_CBC_SHA256</Cipher><Cipher>TLS_DHE_RSA_WITH_AES_256_CBC_SHA</Cipher><Cipher>TLS_DHE_RSA_WITH_AES_128_CBC_SHA256</Cipher><Cipher>TLS_DHE_RSA_WITH_AES_128_CBC_SHA</Cipher><Cipher>SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA</Cipher><Cipher>TLS_RSA_WITH_AES_256_GCM_SHA384</Cipher><Cipher>TLS_RSA_WITH_AES_128_GCM_SHA256</Cipher><Cipher>TLS_RSA_WITH_AES_256_CBC_SHA256</Cipher><Cipher>TLS_RSA_WITH_AES_256_CBC_SHA</Cipher><Cipher>TLS_RSA_WITH_AES_128_CBC_SHA256</Cipher><Cipher>TLS_RSA_WITH_AES_128_CBC_SHA</Cipher><Cipher>SSL_RSA_WITH_3DES_EDE_CBC_SHA</Cipher></SupportedCipherSuites></TLS></Xtended></GenericTLS></genericTLS></Http><FilterOutRfc1767ContentType>false</FilterOutRfc1767ContentType><Xtended><TLS><globalSSLTLS>true</globalSSLTLS><protocol><TLSv1.3>true</TLSv1.3><TLSv1.2>true</TLSv1.2><TLSv1.1>true</TLSv1.1><TLSv1>true</TLSv1></protocol></TLS></Xtended><DataAddress><Urls><Url>http://10.132.61.242:9080/msgsrv/naesb</Url></Urls><User></User><Password></Password><Proxy></Proxy></DataAddress><ErrorAddress><Urls><Url>http://10.132.61.242:9080/msgsrv/naesb</Url></Urls><User></User><Password></Password><Proxy></Proxy></ErrorAddress><Retries><Connection>1</Connection><Delay>30</Delay></Retries><DocumentPartyDetermination>message</DocumentPartyDetermination><ProtocolVersion>NAESB_31</ProtocolVersion><MutuallyAgreedToRefnum>true</MutuallyAgreedToRefnum><ProtocolTimeout>4</ProtocolTimeout></NAESBOutbound>]]></data>\r\n"
							+ "      </urn:addTransport>\r\n" + "   </soapenv:Body>\r\n" + "</soapenv:Envelope>";

					if (filecopy) {
						TX = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:WsAgentConfigServer\">\r\n"
								+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>\r\n"
								+ "      <urn:addTransport soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\r\n"
								+ "         <loginName xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
								+ un + "</loginName>\r\n"
								+ "         <password xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
								+ pw + "</password>\r\n" + "         <partyId xsi:type=\"xsd:long\">" + partyId
								+ "</partyId>\r\n"
								+ "         <name xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">File Copy</name>\r\n"
								+ "         <agentTypeCode xsi:type=\"xsd:long\">7</agentTypeCode>\r\n"
								+ "  <scheduleData xsi:type=\"xsd:string\"><![CDATA[<Schedule><outer><daily><every>1</every></daily></outer><inner/><exclude /><active>on</active></Schedule>]]></scheduleData>\r\n"
								+ "         <data xsi:type=\"xsd:string\"><![CDATA[<Filecopy><Format>%BASE%%.EXT%</Format><DestinationDir>/biz/DMI/</DestinationDir><PreserveSubDirectories>false</PreserveSubDirectories><TmpName></TmpName><Exists>Overwrite</Exists><concurrentConnection>0</concurrentConnection></Filecopy>]]></data>\r\n"
								+ "\r\n" + "      </urn:addTransport>\r\n" + "   </soapenv:Body>\r\n"
								+ "</soapenv:Envelope>";
					}

					/**
					 * Transport
					 */
					RestCall(host + "/axis/services/AgentConfigServer", TX);
					if (filecopy) {
						System.out.println("Created File copy for Party:" + value);
					} else {
						System.out.println("Created NAESB for Party:" + value);
					}
				}

			}
		}
	}

	private static Document convertStringToXMLDocument(String xmlString) {
		// Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try {
			// Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			// Parse the content to Document object
			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String RestCall(String hostUrl, String body) {
		try {
			URL url = new URL(hostUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(50000);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");
			conn.setRequestProperty("SOAPAction", "");
			byte[] outputInBytes = body.getBytes("UTF-8");
			OutputStream os = conn.getOutputStream();
			os.write(outputInBytes);
			os.close();
			System.out.println(conn.getResponseCode());

			BufferedReader br = null;
			if (conn.getResponseCode() == 200) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				return br.lines().collect(Collectors.joining());
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				throw new Exception(br.lines().collect(Collectors.joining()));
			}
		} catch (Exception e) {
			System.err.println("Host: " + host + " Error: " + e.getMessage());
		}
		return null;
	}

}
