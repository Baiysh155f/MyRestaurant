package myrestaurant.service.impl;

import lombok.RequiredArgsConstructor;
import myrestaurant.dto.request.stopList.StopListRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.stopList.StopListResponse;
import myrestaurant.entity.MenuItem;
import myrestaurant.entity.StopList;
import myrestaurant.exceptions.NotFoundExceptionId;
import myrestaurant.repository.MenuItemRepository;
import myrestaurant.repository.StopListRepository;
import myrestaurant.service.StopListService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Service
@RequiredArgsConstructor
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public List<StopListResponse> getStopLists(Long menuItemId) {
        return stopListRepository.findByMenuItemId(menuItemId);
    }

    @Override
    public SimpleResponse create(Long menuItemId, StopListRequest stopListRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new NotFoundExceptionId("Menu item with id = " + menuItemId + " is not found!"));
        for (StopList stopList : menuItem.getStopList()) {
            if (stopList.getDate().equals(stopListRequest.getDate())) {
                return new SimpleResponse(HttpStatus.BAD_REQUEST, "Stop list for this day already exists!");
            }
        }
        StopList stopList = new StopList();
        stopList.setReason(stopListRequest.getReason());
        stopList.setDate(stopListRequest.getDate());
        stopList.setMenuItem(menuItem);
        stopListRepository.save(stopList);
        return new SimpleResponse(HttpStatus.OK, "Stop list created!");
    }

    @Override
    public SimpleResponse update(Long menuItemId, Long stopListId, StopListRequest stopListRequest) {

        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new NotFoundExceptionId("Menu item with id = " + menuItemId + " is not found!"));
        for (StopList stopList : menuItem.getStopList()) {
            if (stopList.getDate().equals(stopListRequest.getDate())) {
                return new SimpleResponse(HttpStatus.BAD_REQUEST, "Stop list for this day already exists!");
            }
        }
        StopList stopList = stopListRepository.findById(stopListId)
                .orElseThrow(() -> new NotFoundExceptionId("StopList with id = " + menuItemId + " is not found!"));
        stopList.setReason(stopListRequest.getReason());
        stopList.setDate(stopListRequest.getDate());
        stopList.setMenuItem(menuItem);
        stopListRepository.save(stopList);
        return new SimpleResponse(HttpStatus.OK, "Stop list created!");
    }

    @Override
    public SimpleResponse delete(Long menuItemId, Long stopListId) {
        if (!stopListRepository.existsById(stopListId)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Stop list with id - " + stopListId + " doesn't exists!")
                    .build();
        }
        StopList stopList = stopListRepository.findById(stopListId).orElseThrow(() ->
                new NotFoundExceptionId("Stop list with id = " + stopListId + " doesn't exists!"));
        stopListRepository.delete(stopList);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Stop list with id - " + stopListId + " is deleted!")
                .build();
    }
}
