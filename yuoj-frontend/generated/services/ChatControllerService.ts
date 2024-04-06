/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AddFriendRequest } from '../models/AddFriendRequest';
import type { BaseResponse_boolean_ } from '../models/BaseResponse_boolean_';
import type { BaseResponse_Groups_ } from '../models/BaseResponse_Groups_';
import type { BaseResponse_long_ } from '../models/BaseResponse_long_';
import type { BaseResponse_Page_ChatMessage_ } from '../models/BaseResponse_Page_ChatMessage_';
import type { BaseResponse_Page_GroupVO_ } from '../models/BaseResponse_Page_GroupVO_';
import type { BaseResponse_Page_long_ } from '../models/BaseResponse_Page_long_';
import type { BaseResponse_Page_UserFriendVO_ } from '../models/BaseResponse_Page_UserFriendVO_';
import type { CreateGroupRequest } from '../models/CreateGroupRequest';
import type { DeleteUnreadRecordRequest } from '../models/DeleteUnreadRecordRequest';
import type { GetMessagesRequest } from '../models/GetMessagesRequest';
import type { JoinGroupRequest } from '../models/JoinGroupRequest';
import type { ListFriendsRequest } from '../models/ListFriendsRequest';
import type { ListGroupMemberRequest } from '../models/ListGroupMemberRequest';
import type { ListGroupRequest } from '../models/ListGroupRequest';
import type { SearchGroupRequest } from '../models/SearchGroupRequest';
import type { UpdateGroupRequest } from '../models/UpdateGroupRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ChatControllerService {

    /**
     * addFriend
     * @param addFriendRequest addFriendRequest
     * @returns BaseResponse_long_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static addFriendUsingPost(
addFriendRequest: AddFriendRequest,
): CancelablePromise<BaseResponse_long_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat/friend/add',
            body: addFriendRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * agreeFriend
     * @param id id
     * @returns BaseResponse_boolean_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static agreeFriendUsingPost(
id?: number,
): CancelablePromise<BaseResponse_boolean_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat/friend/agree',
            query: {
                'id': id,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * deleteFriend
     * @param id id
     * @returns BaseResponse_boolean_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static deleteFriendUsingPost(
id?: number,
): CancelablePromise<BaseResponse_boolean_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat/friend/delete',
            query: {
                'id': id,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * listFriends
     * @param listFriendsRequest listFriendsRequest
     * @returns BaseResponse_Page_UserFriendVO_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static listFriendsUsingPost(
listFriendsRequest: ListFriendsRequest,
): CancelablePromise<BaseResponse_Page_UserFriendVO_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat/friend/list',
            body: listFriendsRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * createGroup
     * @param createGroupRequest createGroupRequest
     * @returns BaseResponse_long_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static createGroupUsingPost(
createGroupRequest: CreateGroupRequest,
): CancelablePromise<BaseResponse_long_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat/group/create',
            body: createGroupRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * deleteGroup
     * @param id id
     * @returns BaseResponse_boolean_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static deleteGroupUsingPost(
id?: number,
): CancelablePromise<BaseResponse_boolean_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat/group/delete',
            query: {
                'id': id,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * joinGroup
     * @param joinGroupRequest joinGroupRequest
     * @returns BaseResponse_long_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static joinGroupUsingPost(
joinGroupRequest: JoinGroupRequest,
): CancelablePromise<BaseResponse_long_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat/group/join',
            body: joinGroupRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * leaveGroup
     * @param id id
     * @returns BaseResponse_boolean_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static leaveGroupUsingPost(
id?: number,
): CancelablePromise<BaseResponse_boolean_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat/group/leave',
            query: {
                'id': id,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * listGroups
     * @param listGroupRequest listGroupRequest
     * @returns BaseResponse_Page_GroupVO_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static listGroupsUsingPost(
listGroupRequest: ListGroupRequest,
): CancelablePromise<BaseResponse_Page_GroupVO_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat/group/list',
            body: listGroupRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * listGroupMembers
     * @param listGroupMemberRequest listGroupMemberRequest
     * @returns BaseResponse_Page_long_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static listGroupMembersUsingPost(
listGroupMemberRequest: ListGroupMemberRequest,
): CancelablePromise<BaseResponse_Page_long_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat/group/list/members',
            body: listGroupMemberRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * searchGroups
     * @param searchGroupRequest searchGroupRequest
     * @returns BaseResponse_Page_GroupVO_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static searchGroupsUsingPost(
searchGroupRequest: SearchGroupRequest,
): CancelablePromise<BaseResponse_Page_GroupVO_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat/group/search',
            body: searchGroupRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * updateGroup
     * @param updateGroupRequest updateGroupRequest
     * @returns BaseResponse_Groups_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateGroupUsingPost(
updateGroupRequest: UpdateGroupRequest,
): CancelablePromise<BaseResponse_Groups_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat/group/update',
            body: updateGroupRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * deleteUnreadRecord
     * @param deleteUnreadRecordRequest deleteUnreadRecordRequest
     * @returns BaseResponse_boolean_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static deleteUnreadRecordUsingPost(
deleteUnreadRecordRequest: DeleteUnreadRecordRequest,
): CancelablePromise<BaseResponse_boolean_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat/message/unread/delete',
            body: deleteUnreadRecordRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * getMessages
     * @param getMessagesRequest getMessagesRequest
     * @returns BaseResponse_Page_ChatMessage_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static getMessagesUsingPost(
getMessagesRequest: GetMessagesRequest,
): CancelablePromise<BaseResponse_Page_ChatMessage_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/chat/messages',
            body: getMessagesRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

}
