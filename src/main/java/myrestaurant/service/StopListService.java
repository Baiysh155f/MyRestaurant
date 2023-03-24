package myrestaurant.service;

import myrestaurant.dto.request.stopList.StopListRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.stopList.StopListResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Service
public interface StopListService {
    List<StopListResponse> getStopLists(Long menuItemId);

    SimpleResponse create(Long menuItemId, StopListRequest stopListRequest);

    SimpleResponse update(Long menuItemId, Long stopListId, StopListRequest stopListRequest);

    SimpleResponse delete(Long stopListId);
}
