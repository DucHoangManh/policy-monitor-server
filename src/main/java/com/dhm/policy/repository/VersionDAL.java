package com.dhm.policy.repository;

import com.dhm.policy.domain.Version;

import java.util.List;
import java.util.Optional;

public interface VersionDAL {
    List<Version> getAll();
    Version getLatest();
    void removeLatest();
}
