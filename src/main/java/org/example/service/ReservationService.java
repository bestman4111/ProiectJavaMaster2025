package org.example.service;

import org.example.dto.request.ReservationCreateRequest;
import org.example.dto.response.ReservationResponse;

public interface ReservationService {
    ReservationResponse create(ReservationCreateRequest request);
}
