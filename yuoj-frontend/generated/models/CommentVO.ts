/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import { ReplyVO } from "./ReplyVO";

export type CommentVO = {
  commentReplies?: Array<ReplyVO>;
  content?: string;
  createTime?: string;
  id?: number;
  likes?: number;
  questionId?: number;
  replies?: number;
  userAvatar?: string;
  userId?: number;
  userName?: string;
};
