package myrestaurant.api;

import myrestaurant.dto.request.stopList.StopListRequest;
import myrestaurant.dto.response.SimpleResponse;
import myrestaurant.dto.response.stopList.StopListResponse;
import myrestaurant.service.StopListService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kurstan
 * @created at 20.03.2023 10:29
 */
@RestController
@RequestMapping("/api/stopLists")
public class StopListApi {
    private final StopListService stopListService;

    public StopListApi(StopListService stopListService) {
        this.stopListService = stopListService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/{menuItemId}")
    public List<StopListResponse> getStopLists(@PathVariable Long menuItemId) {
        return stopListService.getStopLists(menuItemId);
    }

    @PostMapping("/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public SimpleResponse create(@PathVariable Long menuItemId,
                                 @RequestBody StopListRequest stopListRequest) {
        return stopListService.create(menuItemId, stopListRequest);
    }

    @PutMapping("/{stopListId}/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse update(@PathVariable Long stopListId,
                          @PathVariable Long menuItemId,
                          @RequestBody StopListRequest stopListRequest) {
        return stopListService.update(menuItemId, stopListId, stopListRequest);
    }

    @DeleteMapping("/{stopListId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse delete(@PathVariable Long stopListId) {
        return stopListService.delete(stopListId);
    }
}
