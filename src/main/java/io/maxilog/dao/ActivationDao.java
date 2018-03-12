package io.maxilog.dao;

import io.maxilog.entity.Activation;

/**
 * Created by mossa on 01/12/2017.
 */
public interface ActivationDao {
    public Activation getActivation(String keyActivation);
    public String createActivation(Activation activation);
    public void  deleteActivation(String keyActivation);
}
