"use strict";

import { Response, Request, NextFunction } from "express";
import { Stream, StreamDocument } from "../../models/Stream";


/**
 * List of API examples.
 * @route GET /api
 */
export const getStreams = (req: Request, res: Response) => {
    Stream.find((error, streamDocuments) => {
        console.log("Got documents:", streamDocuments.length);
        streamDocuments.forEach(document => console.log(document));
    });
};

export const createStream = async (req: Request, res: Response) => {
    const stream = req.body as StreamDocument;
    try {
        await stream.save();
        res.json({
            message: "Success!"
        }).send(200);
    } catch (error) {
        console.error("error:", error);
        res.json({
            error: error.message
        }).send(500);
    }
};