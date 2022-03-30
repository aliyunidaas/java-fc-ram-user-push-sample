package com.aliyunidaas.sync.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Below code is from org.springframework:spring-core:5.2.12, With some modification
 * Reference: org.springframework.security.web.util.matcher.IpAddressMatcher
 *
 * Matches a request based on IP Address or subnet mask matching against the remote
 * address.
 * <p>
 * Both IPv6 and IPv4 addresses are supported, but a matcher which is configured with an
 * IPv4 address will never match a request which returns an IPv6 address, and vice-versa.
 *
 * @author Luke Taylor
 * @since 3.0.2
 */
public class IpMatcherImpl implements IpMatcher {
    private final int nMaskBits;
    private final InetAddress requiredAddress;

    /**
     * Takes a specific IP address or a range specified using the IP/Netmask (e.g.
     * 192.168.1.0/24 or 202.24.0.0/14).
     *
     * @param ipAddress the address or range of addresses from which the request must
     *                  come.
     */
    public IpMatcherImpl(String ipAddress) {
        if (ipAddress.indexOf('/') > 0) {
            final String[] addressAndMask = StringUtil.split(ipAddress, "/");
            //noinspection ConstantConditions
            ipAddress = addressAndMask[0];
            nMaskBits = Integer.parseInt(addressAndMask[1]);
        } else {
            nMaskBits = -1;
        }
        requiredAddress = parseAddress(ipAddress);
        if (!(requiredAddress.getAddress().length * 8 >= nMaskBits)) {
            throw new IllegalArgumentException(
                    String.format("IP address %s is too short for bitmask of length %d", ipAddress, nMaskBits));
        }
    }

    @Override
    public boolean matches(String address) {
        final InetAddress remoteAddress = parseAddress(address);

        if (!requiredAddress.getClass().equals(remoteAddress.getClass())) {
            return false;
        }

        if (nMaskBits < 0) {
            return remoteAddress.equals(requiredAddress);
        }

        final byte[] remAddr = remoteAddress.getAddress();
        final byte[] reqAddr = requiredAddress.getAddress();

        final int nMaskFullBytes = nMaskBits / 8;
        final byte finalByte = (byte)(0xFF00 >> (nMaskBits & 0x07));

        for (int i = 0; i < nMaskFullBytes; i++) {
            if (remAddr[i] != reqAddr[i]) {
                return false;
            }
        }

        if (finalByte != 0) {
            return (remAddr[nMaskFullBytes] & finalByte) == (reqAddr[nMaskFullBytes] & finalByte);
        }

        return true;
    }

    private InetAddress parseAddress(String address) {
        try {
            return InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("Failed to parse address" + address, e);
        }
    }
}
