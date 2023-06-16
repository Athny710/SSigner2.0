package org.oefa.com.ssigner.infra.output.port;

import org.oefa.com.ssigner.domain.util.Log;

public interface LogPort {
    public void setInfo(Log log);
    public void setWarn(Log log);
    public void setError(Log log);
}
