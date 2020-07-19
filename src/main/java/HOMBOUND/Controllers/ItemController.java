package HOMBOUND.Controllers;

import HOMBOUND.Models.HomBoundRequest;
import HOMBOUND.Models.HomBoundUser;
import HOMBOUND.Models.Item;
import HOMBOUND.Models.enums.HomBoundStatus;
import HOMBOUND.Models.enums.ItemType;
import HOMBOUND.Repositories.HomBoundRequestRepository;
import HOMBOUND.Repositories.HomBoundUserRepository;
import HOMBOUND.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@CrossOrigin
@RequestMapping("api/requests")
@RestController
public class ItemController {
    @Autowired
    public ItemRepository itemRepository;

    @Autowired
    public HomBoundRequestRepository homBoundRequestRepository;

    @Autowired
    public HomBoundUserRepository homBoundUserRepository;

    @PostMapping(path="/{requestId}/add")
    public Item addItemToRequest(@PathVariable("requestId") Long requestId, @RequestBody Item item){
        Optional<HomBoundRequest> request = homBoundRequestRepository.findById(requestId);
        checkForRequestErrors(requestId, item);
        item.setHomBoundRequest(request.get());
        return itemRepository.save(item);
    }

    @PutMapping(path="/{requestId}/item/{itemId}/update")
    public Item updateItemInRequest(@PathVariable("requestId") Long requestId, @PathVariable("itemId") Long itemId , @RequestBody ItemWithUserId itemWithUserId){
        Optional<Item> itemToUpdate = itemRepository.findById(itemId);
        if(!itemToUpdate.isPresent()){
            throw new Error("Item with id " + itemId + " does not exist!");
        }
        if(canUserUpdate(itemWithUserId.userId, requestId)) {
            Item item = itemWithUserId.item;
            if (item.getCategory() != null) {
                itemToUpdate.get().setCategory(item.getCategory());
            }
            checkForRequestErrors(requestId, itemToUpdate.get());
            if (item.getName() != null) {
                itemToUpdate.get().setName(item.getName());
            }

            if (item.getQuantity() != null) {
                itemToUpdate.get().setQuantity(item.getQuantity());
            }
            return itemRepository.save(itemToUpdate.get());
        } else {
            throw new Error("User " + itemWithUserId.userId + " does not have permission to edit this request.");
        }
    }

    public void checkForRequestErrors(Long requestId, Item item){
        Optional<HomBoundRequest> request = homBoundRequestRepository.findById(requestId);
        if (!request.isPresent()) {
            String errMsg = "Could not find HomBound Request with id " + requestId + "!";
            throw new Error(errMsg);
        }
        if (!request.get().getStatus().equals(HomBoundStatus.Open.name())) {
            String errMsg = "Request " + requestId + " has already been submitted and can no longer take new items.";
            throw new Error(errMsg);
        }
        ItemType[] possibleItemOptions = ItemType.values();
        ItemType itemType = ItemType.valueOf(item.getCategory());
        if (!Arrays.asList(possibleItemOptions).contains(itemType)) {
            String errMsg = "Item type " + item.getCategory() + " does not exist.";
            throw new Error(errMsg);
        }
    }

    public boolean canUserUpdate(Long userId, Long requestId){
        Optional<HomBoundRequest> request = homBoundRequestRepository.findById(requestId);
        Optional<HomBoundUser> user = homBoundUserRepository.findById(userId);
        if(!request.isPresent() || !user.isPresent()) throw new Error("Could not find user " + userId + " or request " + requestId + " in order to verify permission.");
        return user.get().getId() == request.get().getRequestedBy().getId();
    }

    // Custom Types
    private static class ItemWithUserId {
        public Long userId;
        public Item item;
        public ItemWithUserId(Long userId, Item item) {
            this.userId = userId;
            this.item = item;
        }
    }
    private static class RequestWithUserId {
        public Long userId;
        public HomBoundRequest request;
        public RequestWithUserId(Long userId, HomBoundRequest request) {
            this.userId = userId;
            this.request = request;
        }
    }
}

