package com.platform.modules.protocol.utils;

import com.platform.modules.protocol.entity.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

@Component
public class TissueUtil {

	/**
	 * 服务器下行信息
	 * @param socket
	 * @param toClientStr
	 */
	public static void sentToClient(Socket socket, String toClientStr) {
		OutputStream outputStream;
		try {
			outputStream = socket.getOutputStream();  // 给这个socket发信息
			outputStream.write(hex2Bytes(toClientStr));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(toClientStr);
	}


	public static String getIpBySocket(Socket socket) {
		return socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
	}

	public static byte[] hex2Bytes(String str) {
		if(str == null || str.trim().equals("")) {
			return new byte[0];
		}

		String[] toClientStrs = str.split(" ");
		byte[] bytes = new byte[toClientStrs.length];
		for(int i = 0; i < toClientStrs.length; i++) {
			bytes[i] = (byte) Integer.parseInt(toClientStrs[i],16);
		}

		return bytes;
	}

	public static String byteToStr(byte[] buffer) {
		try {
			int length = 0;
			for (int i = buffer.length - 1; i >= 0; --i) {
				if (buffer[i] == 0) {
					length++;
				}else {
					break;
				}
			}
			if (length == buffer.length) {
				return "";
			}
			return new String(buffer, 0, buffer.length - length, "UTF-8");
		} catch (Exception e) {
			return "";
		}
	}

	public static String bytesToHexString(byte[] src) {
		StringBuffer sb = new StringBuffer("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v).toUpperCase();
			if (hv.length() < 2) {
				sb.append(0);
			}
			sb.append(hv);
			if (i != src.length - 1) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	public static String intsToHexString(Integer[] src) {
		StringBuffer sb = new StringBuffer("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v).toUpperCase();
			if (hv.length() < 2) {
				sb.append(0);
			}
			sb.append(hv);
			if (i != src.length - 1) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}

	public static BaseProtocol getBaseProtocol(byte[] src) {
		if (src == null || src.length <= 0) {
			return null;
		}
		BaseProtocol baseProtocol = new BaseProtocol();
		Integer[] packetLen = baseProtocol.getPacketLen();
		Integer[] token = baseProtocol.getToken();
		for (int i = 0; i < 9; i++) {
			int v = src[i] & 0xFF;
			switch (i) {
				case 0:
				case 1:
					packetLen[i] = v;
					break;
				case 2:
					baseProtocol.setCommand(getFCE(v));
					break;
				case 3:
					baseProtocol.setVsn(v);
					break;
				case 4:
					baseProtocol.setCheckSum(v);
					break;
				case 5:
				case 6:
				case 7:
				case 8:
					token[i-5] = v;
					break;
			}
		}

		baseProtocol.setPacketLen(packetLen);
		baseProtocol.setToken(token);

		return baseProtocol;
	}

	public static BootProtocol getBootProtocol(byte[] src) {
		if (src == null || src.length <= 0) {
			return null;
		}
		BootProtocol bootProtocol = new BootProtocol();
		Integer[] packetLen = bootProtocol.getPacketLen();
		Integer[] token = bootProtocol.getToken();
		Integer[] rand = bootProtocol.getRand();
		Integer[] magic = bootProtocol.getMagic();
		Integer[] boxIDLen = bootProtocol.getBoxIDLen();
		byte[] boxId = new byte[16];
		// todo 还未添加ReqData
//		Integer[] reqDataLen = bootProtocol.getReqDataLen();
//		bootProtocol.getReqData();
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			switch (i) {
				case 0:
				case 1:
					packetLen[i] = v;
					break;
				case 2:
					bootProtocol.setCommand(getFCE(v));
					break;
				case 3:
					bootProtocol.setVsn(v);
					break;
				case 4:
					bootProtocol.setCheckSum(v);
					break;
				case 5:case 6:case 7:case 8:
					token[i-5] = v;
					break;
				case 9:case 10:case 11:case 12:
					rand[i-9] = v;
					break;
				case 13:case 14:
					magic[i-13] = v;
					break;
				case 15:case 16:
					boxIDLen[i-15] = v;
					break;
				case 17:case 18:case 19:case 20:case 21:case 22:case 23:case 24:
				case 25:case 26:case 27:case 28:case 29:case 30:case 31:case 32:
					boxId[i-17] = src[i];
					break;
				default:
			}
		}

		bootProtocol.setPacketLen(packetLen);
		bootProtocol.setToken(token);
		bootProtocol.setRand(rand);
		bootProtocol.setMagic(magic);
		bootProtocol.setBoxIDLen(boxIDLen);
		bootProtocol.setBoxID(byteToStr(boxId));
		return bootProtocol;
	}



//	public static PowerBankRedis getPowerBankRedis(String vsn, PowerBankRedis powerBankRedis) throws ProtocolException {
//		//类型检查
//		FuncCodeEnum codeEnum = powerBankRedis.getFuncCodeEnum();
//		if (codeEnum == null) {
//			throw new ProtocolException("无指定指令类型");
//		}
//		switch (codeEnum.getCode()) {
//			case 0x62:
//			case 0x63:
//			case 0x64:
//			case 0x65:
//			case 0x67:
//			case 0x68:
//			case 0x69:
//			case 0x6A:
//			case 0x80:
//			case 0x77:
//			case 0x70:
//			case 0x71:
//			case 0x72:
//				break;
//			default:
//				throw new ProtocolException("指定的类型无法主动请求机柜");
//		}
//		System.out.println("类型检查无误");
//
//		if (powerBankRedis.getIp() == null) {
//			throw new ProtocolException("未设IP");
//		}
//
//		Integer[] baseMessage = new Integer[] {0x00, 0x07, codeEnum.getCode(), Integer.valueOf(vsn), 0x00, 0x11, 0x22, 0x33, 0x44};
//		// 该类型必要信息
//		switch (codeEnum.getCode()) {
//			case 0x62: // 查询机柜软件版本号及响应
//			case 0x64: // 查询机柜库存及响应
//			case 0x67: // 远程重启机柜及响应
//			case 0x69: // 查询ICCID
//			case 0x6A: // 查询服务器地址及响应
//			case 0x77: // 查询机柜语音播报音量
//			case 0x72: // 查询机柜网络信息
//				// 这些均无需字段
//				powerBankRedis.setMessage(baseMessage);
//				break;
//			case 0x65: // 借充电宝及响应
//			case 0x80: // 强制弹出充电宝
//				// Slot
//				Integer slot = powerBankRedis.getPbSlotId();
//				if (slot == null)
//					throw new ProtocolException("机柜槽位没有设置");
//
//				Integer[] slotMessage = new Integer[10];
//				System.arraycopy(baseMessage, 0, slotMessage, 0, baseMessage.length);
//				slotMessage[9] = slot;
//				powerBankRedis.setMessage(slotMessage);
//				break;
//			case 0x63: // 设置机柜服务器地址及响应
//				/**
//				 * AddressLen 服务器地址长度 Uint16 2
//				 * Address 服务器地址 String AddressLen
//				 * PortLen 服务器端口长度 Uint16 2
//				 * Port 服务器端口 String PortLen
//				 * Heartbeat 心跳间隔(1~255有效) Byte 1
//				 */
//				break;
//			case 0x68: // 远程升级及响应
//				/**
//				 * FTPAddressLe FTP 服务器地址长度 Uint16 2
//				 * FTPAddress FTP 服务器地址 String FTPAddressLen
//				 * FTPPortLen FTP 服务器端口长度 Uint16 2
//				 * FTPPort FTP 服务器端口 String FTPPortLen
//				 * FileNameLen 文件名长度 Uint16 2
//				 * FileName 文件名 String FileNameLen
//				 * UsernameLen 用户名长度 Uint16 2
//				 * Username 用户名 String UsernameLen
//				 * PasswordLen 密码长度 Uint16 2
//				 * Password 密码 String PasswordLen
//				 */
//				break;
//			case 0x70: // 设置机柜语音播报音量
//				// Lvl 音量大小(0 到 15) Byte 1
//				Integer level = powerBankRedis.getLevel();
//				if (level == null) {
//					throw new ProtocolException("没有设置音量数值");
//				}
//				if (level < 0 || level > 15) {
//					throw new ProtocolException("音量必须在[0,15]区间");
//				}
//				Integer[] levelMessage = new Integer[10];
//				System.arraycopy(baseMessage, 0, levelMessage, 0, baseMessage.length);
//				levelMessage[9] = level;
//				powerBankRedis.setMessage(levelMessage);
//				break;
//		}
//		return powerBankRedis;
//	}

	private static FuncCodeEnum getFCE(int v) {
		String fce = "CODE_";
		switch (v) {
			case 0x60:
				fce += "0x60";
				break;
			case 0x61:
				fce += "0x61";
				break;
			case 0x62:
				fce += "0x62";
				break;
			case 0x63:
				fce += "0x63";
				break;
			case 0x64:
				fce += "0x64";
				break;
			case 0x65:
				fce += "0x65";
				break;
			case 0x66:
				fce += "0x66";
				break;
			case 0x67:
				fce += "0x67";
				break;
			case 0x68:
				fce += "0x68";
				break;
			case 0x69:
				fce += "0x69";
				break;
			case 0x6A:
				fce += "0x6A";
				break;
			case 0x80:
				fce += "0x80";
				break;
			case 0x77:
				fce += "0x77";
				break;
			case 0x70:
				fce += "0x70";
				break;
			case 0x71:
				fce += "0x71";
				break;
			case 0x72:
				fce += "0x72";
				break;
			default:
		}
		if (fce.equals("CODE_")) {
			return null;
		}
		return FuncCodeEnum.valueOf(fce);
	}

}