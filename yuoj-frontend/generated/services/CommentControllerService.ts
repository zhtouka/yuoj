/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponse_long_ } from "../models/BaseResponse_long_";
import type { BaseResponse_Page_CommentVO_ } from "../models/BaseResponse_Page_CommentVO_";
import type { CommentAddRequest } from "../models/CommentAddRequest";
import type { CommentQueryRequest } from "../models/CommentQueryRequest";

import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";
import { ReplyQueryRequest } from "../models/ReplyQueryRequest";
import { BaseResponse_Page_ReplyVO_ } from "../models/BaseResponse_Page_ReplyVO_";

export class CommentControllerService {
  /**
   * addComment
   * @param commentAddRequest commentAddRequest
   * @returns BaseResponse_long_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static addCommentUsingPost(
    commentAddRequest: CommentAddRequest
  ): CancelablePromise<BaseResponse_long_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/comment/add",
      body: commentAddRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }

  /**
   * getCommentsByQuestionId
   * @param commentQueryRequest commentQueryRequest
   * @returns BaseResponse_Page_CommentVO_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static getCommentsByQuestionIdUsingPost(
    commentQueryRequest: CommentQueryRequest
  ): CancelablePromise<BaseResponse_Page_CommentVO_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/comment/list/page",
      body: commentQueryRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }

  /**
   * getCommentsByQuestionId
   * @param replyQueryRequest replyQueryRequest
   * @returns BaseResponse_Page_CommentVO_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static getRepliesByQuestionIdUsingPost(
    replyQueryRequest: ReplyQueryRequest
  ): CancelablePromise<BaseResponse_Page_ReplyVO_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/comment/list/page/reply",
      body: replyQueryRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
}
