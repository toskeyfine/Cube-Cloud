package com.toskey.cube.common.license.support;

import com.toskey.cube.common.core.util.StringUtils;
import de.schlichtherle.license.*;
import de.schlichtherle.xml.GenericCertificate;
import de.schlichtherle.xml.XMLConstants;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CubeLicenseManager
 *
 * @author toskey
 * @version 1.0.0
 */
public class CubeLicenseManager extends de.schlichtherle.license.LicenseManager {

    public CubeLicenseManager(LicenseParam param) {
        super(param);
    }

    @Override
    protected synchronized byte[] create(LicenseContent licenseContent, LicenseNotary licenseNotary) throws Exception {
        initialize(licenseContent);
        this.validateCreate(licenseContent);
        final GenericCertificate certificate = licenseNotary.sign(licenseContent);
        return getPrivacyGuard().cert2key(certificate);
    }

    @Override
    protected synchronized LicenseContent install(byte[] bytes, LicenseNotary licenseNotary) throws Exception {
        final GenericCertificate certificate = getPrivacyGuard().key2cert(bytes);
        licenseNotary.verify(certificate);
        final LicenseContent content = (LicenseContent) this.load(certificate.getEncoded());
        this.validate(content);
        setLicenseKey(bytes);
        setCertificate(certificate);
        return content;
    }

    @Override
    protected synchronized LicenseContent verify(LicenseNotary licenseNotary) throws Exception {
        final byte[] key = getLicenseKey();
        if (null == key) {
            throw new NoLicenseInstalledException(getLicenseParam().getSubject());
        }

        GenericCertificate certificate = getPrivacyGuard().key2cert(key);
        licenseNotary.verify(certificate);
        final LicenseContent content = (LicenseContent) this.load(certificate.getEncoded());
        this.validate(content);
        setCertificate(certificate);
        return content;
    }

    protected synchronized void validateCreate(final LicenseContent licenseContent) throws LicenseException {
        final Date now = new Date();
        final Date notBefore = licenseContent.getNotBefore();
        final Date notAfter = licenseContent.getNotAfter();

        if (null != notAfter && now.after(notAfter)) {
            throw new LicenseException("证书失效时间不得小于当前时间");
        }
        if (null != notBefore && null != notAfter && notAfter.before(notBefore)) {
            throw new LicenseException("证书失效时间不能晚于失效时间");
        }
    }

    @Override
    protected synchronized void validate(LicenseContent licenseContent) throws LicenseContentException {
        super.validate(licenseContent);
        Object extraObj = licenseContent.getExtra();
        if (extraObj != null) {
            LicenseExtraInfo extraInfo = (LicenseExtraInfo) extraObj;
            if (Objects.nonNull(extraInfo)) {
                String mac = extraInfo.getMac();
                if (StringUtils.isNotBlank(mac)) {
                    if (!compareMac(mac)) {
                        throw new LicenseException("本机MAC地址与授权不匹配，请联系发行方重新获取授权.");
                    }
                }
                // 校验IP地址
                String host = extraInfo.getHost();
                if (StringUtils.isNotBlank(host)) {
                    try {
                        List<Inet4Address> inet4AddressList = getLocalIp4AddressFromNetworkInterface();
                        if (inet4AddressList != null && !inet4AddressList.isEmpty()) {
                            long count = inet4AddressList.stream().filter(addr -> addr.getHostAddress().equals(host)).count();
                            if (count == 0) {
                                throw new LicenseException("本机IP地址与授权不匹配，请联系发行方重新获取授权.");
                            }
                        }
                    } catch (Exception e) {
                        throw new LicenseException("无法获取本机IP地址，请确保当前服务器的网卡安装正确.");
                    }
                }
            }
        }
    }

    private Object load(String encoded) {
        BufferedInputStream bis = null;
        XMLDecoder decoder = null;
        try {
            bis = new BufferedInputStream(new ByteArrayInputStream(encoded.getBytes(XMLConstants.XML_CHARSET)));
            decoder = new XMLDecoder(new BufferedInputStream(bis, XMLConstants.DEFAULT_BUFSIZE), null, null);
            return decoder.readObject();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            try {
                if (decoder != null) {
                    decoder.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * mac地址验证
     *
     * @param srcMac 待验证的mac地址（无分隔符）
     * @return
     * @throws LicenseException
     */
    private boolean compareMac(String srcMac) throws LicenseException {
        List<Inet4Address> addressList = null;
        try {
            addressList = getLocalIp4AddressFromNetworkInterface();
        } catch (Exception e) {
            throw new LicenseException("无法获取本机MAC地址，请确保当前服务器的网卡安装正确.");
        }
        List<String> macList = addressList.stream().map(address -> {
            byte[] mac = new byte[0];
            try {
                mac = NetworkInterface.getByInetAddress(address).getHardwareAddress();
            } catch (SocketException e) {
                e.printStackTrace();
                return null;
            }
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
//                sb.append("-");
                }
                //字节转换为整数
                int temp = mac[i] & 0xff;
                String str = Integer.toHexString(temp);
                if (str.length() == 1) {
                    sb.append("0" + str);
                } else {
                    sb.append(str);
                }
            }
            return sb.toString();
        }).collect(Collectors.toList());

        long count = macList.stream().filter(mac -> mac.equalsIgnoreCase(srcMac)).count();
        return count > 0;
    }

    /**
     * 通过网卡获取Inet4Address集合
     *
     * @return
     * @throws LicenseException
     */
    private List<Inet4Address> getLocalIp4AddressFromNetworkInterface() throws LicenseException {
        List<Inet4Address> addresses = new ArrayList<>(1);

        // 所有网络接口信息
        Enumeration<NetworkInterface> networkInterfaces = null;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new LicenseException("读取本地网卡失败，请确保当前服务器的网卡安装正确.");
        }
        if (Objects.isNull(networkInterfaces)) {
            return addresses;
        }
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            //滤回环网卡、点对点网卡、非活动网卡、虚拟网卡并要求网卡名字是eth或ens开头
            if (!isValidInterface(networkInterface)) {
                continue;
            }

            // 所有网络接口的IP地址信息
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                // 判断是否是IPv4，并且内网地址并过滤回环地址.
                if (isValidAddress(inetAddress)) {
                    addresses.add((Inet4Address) inetAddress);
                }
            }
        }
        return addresses;
    }

    /**
     * 验证IP地址是否符合要求
     *
     * @param address
     * @return
     */
    private boolean isValidAddress(InetAddress address) {
        return address instanceof Inet4Address && address.isSiteLocalAddress() && !address.isLoopbackAddress();
    }

    /**
     * 验证网卡是否符合要求
     *
     * @param ni
     * @return
     * @throws LicenseException
     */
    private boolean isValidInterface(NetworkInterface ni) throws LicenseException {
        try {
            return !ni.isLoopback() && !ni.isPointToPoint() && ni.isUp() && !ni.isVirtual()
                    && (ni.getName().startsWith("eth") || ni.getName().startsWith("ens"));
        } catch (SocketException e) {
            throw new LicenseException("验证网卡失败.");
        }
    }
}
